package com.github.alexsniffin.packerman.impl.Computation

import com.github.alexsniffin.packerman.impl.Error._
import com.github.alexsniffin.packerman.impl.Pack

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

sealed case class ComputedResult[In, POut](input: In, updatedPackingProp: POut)

trait ComputationProps[In, POut] {
  def compute(): Either[Error, Seq[ComputedResult[In, POut]]]
}

class Computation[In, GOut, POut](val pack: Pack[In, GOut, POut])(implicit n: Numeric[POut]) extends ComputationProps[In, POut] {
  def compute(): Either[Error, Seq[ComputedResult[In, POut]]] = pack match {
    case Pack(_, groupFn, packFn, distributeFn) if groupFn.isEmpty || packFn.isEmpty || distributeFn.isEmpty =>
      Left(MissingParametersError("Required parameters are missing from pack"))
    case Pack(input, groupFn, packFn, distributeFn) => Try(Computation.maxBinPacking(input.get, groupFn.get, packFn.get, distributeFn.get)) match {
      case Success(result: Seq[ComputedResult[In, POut]]) => Right(result)
      case Failure(fail) => Left(ThrowableError(fail.getMessage))
    }
    case _ => Left(UnknownError("Unknown error"))
  }
}

object Computation {
  def apply[In, GOut, POut](pack: Pack[In, GOut, POut])(implicit n: Numeric[POut]): Computation[In, GOut, POut] = new Computation[In, GOut, POut](pack)

  private[packerman] def maxBinPacking[In, GOut, POut](
      seq: Seq[In],
      groupFn: Pack.Grouping[In, GOut],
      packFn: Pack.Packing[In, POut],
      distributeAlgorithm: DistributionAlgorithm)(implicit n: Numeric[POut]): Either[Error, Seq[ComputedResult[In, POut]]] = {
    val inputMappedSeq = seq.map(in => ComputedResult(in, packFn.apply(in)))

    val sumOfPackValue: Double = n.toDouble(seq.map(packFn).sum)

    val groups: Map[GOut, Seq[ComputedResult[In, POut]]] = inputMappedSeq.groupBy(x => groupFn(x.input))

    distributeAlgorithm match {
      case UniformDistribution(weighted, limit) =>
        uniformDistribution(weighted, limit, packFn, sumOfPackValue, groups) match {
          case Right(result) => Right(result.values.flatten.toSeq)
          case Left(err) => Left(err)
        }
      case _ => Left(InvalidAlgorithmError("Invalid distribution algorithm specified"))
    }
  }

  private[packerman] def uniformDistribution[In, GOut, POut](
      weighted: Boolean,
      limit: Double,
      packFn: Pack.Packing[In, POut],
      sumOfPackValue: Double,
      groups: Map[GOut, Seq[ComputedResult[In, POut]]])(implicit n: Numeric[POut]): Either[Error, Map[GOut, Seq[ComputedResult[In, POut]]]] = {
    if (groups.size * limit < 1) Left(InvalidAlgorithmParameterError(s"Limit parameter is too low, minimal limit: ${(1 / groups.size).toDouble}"))

    @tailrec
    def reduceAndCompute(
        notLimited: Option[Map[GOut, Seq[ComputedResult[In, POut]]]],
        limited: Option[Map[GOut, Seq[ComputedResult[In, POut]]]] = None)(implicit n: Numeric[POut]): Option[Map[GOut, Seq[ComputedResult[In, POut]]]] = {
      if (notLimited.isEmpty || notLimited.get.size == 1) return Some(notLimited.get ++ limited.get)

      val limitAmt: Double = limit * sumOfPackValue

      val groupSums: Map[GOut, Double] = groups.map {
        case (k, v) =>
          val sumOfGroup: Double = n.toDouble(v.map(group => packFn(group.input)).sum)

          (k, sumOfGroup)
      }

      val bucketExcessSum: Double = groupSums.filter(_._2 > limitAmt).map {
        case (_, v: Double) => v - limitAmt
      }.sum

      val updatedLimited: Option[Map[GOut, Seq[ComputedResult[In, POut]]]] = Some(groupSums.filter(_._2 > limitAmt).map {
        case (k, v) =>
          val amtOver: Double = v - limitAmt
          val amtEa: Double = amtOver / v

          (k, groups(k).map(i => i.copy(updatedPackingProp = (n.toDouble(i.updatedPackingProp) - amtEa).asInstanceOf[POut])))
      })

      val updatedNotLimited: Option[Map[GOut, Seq[ComputedResult[In, POut]]]] = Some(groupSums.filter(_._2 <= limitAmt).map {
        case (k, v) =>
          val amtEa: Double = bucketExcessSum / v

          (k, groups(k).map(i => i.copy(updatedPackingProp = (n.toDouble(i.updatedPackingProp) + amtEa).asInstanceOf[POut])))
      })

      reduceAndCompute(updatedNotLimited, updatedLimited)
    }

    val result: Option[Map[GOut, Seq[ComputedResult[In, POut]]]] = reduceAndCompute(Some(groups))

    if (result.isEmpty) Left(AlgorithmError("Error with computing uniformDistribution"))
    else Right(result.get)
  }
}
