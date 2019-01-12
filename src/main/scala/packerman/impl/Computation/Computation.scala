package packerman.impl.Computation

import java.security.InvalidAlgorithmParameterException

import packerman.impl._

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

sealed case class InputMapper[In, POut <: Double](input: In, updatedPackingProp: POut)

trait ComputationMonad[In] {
  def compute(): Either[Seq[In], Error]
}

class Computation[In, GOut, POut <: Double](pack: Pack[In, GOut, POut]) extends ComputationMonad[In] {
  def compute(): Either[Seq[In], Error] = pack match {
    case Pack(_, groupFn, packFn, distributeFn) if groupFn.isEmpty || packFn.isEmpty || distributeFn.isEmpty =>
      Right(MissingParametersError("Required parameters are missing from pack"))
    case Pack(input, groupFn, packFn, distributeFn) => Try(Computation.maxBinPacking(input.get, groupFn.get, packFn.get, distributeFn.get)) match {
      case Success(result: Seq[In]) => Left(result)
      case Failure(fail) => Right(ThrowableError(fail.getMessage))
    }
    case _ => Right(UnknownError("Unknown error"))
  }
}

object Computation {
  def apply[In, GOut, POut <: Double](pack: Pack[In, GOut, POut]): Computation[In, GOut, POut] = new Computation[In, GOut, POut](pack)

  def maxBinPacking[In, GOut, POut <: Double](
      seq: Seq[In],
      groupFn: Pack.Grouping[In, GOut],
      packFn: Pack.Packing[In, POut],
      distributeAlgorithm: DistributionAlgorithm): Seq[InputMapper[In, POut]] = {

    val inputMappedSeq = seq.map(in => InputMapper(in, packFn.apply(in)))

    val sumOfPackValue = seq.map(packFn).reduce[Double](_ + _)

    val groups: Map[GOut, Seq[InputMapper[In, POut]]] = inputMappedSeq.groupBy(x => groupFn(x.input))

    val distributed = distributeAlgorithm match {
      case UniformDistribution(weighted, limit) => uniformDistribution(weighted, limit, packFn, sumOfPackValue, groups)
      case _ => throw new InvalidAlgorithmParameterException("Invalid distribution algorithm specified")
    }

    distributed.values.flatten.toSeq
  }

  def uniformDistribution[In, GOut, POut <: Double](
      weighted: Boolean,
      limit: Double,
      packFn: Pack.Packing[In, POut],
      sumOfPackValue: Double,
      groups: Map[GOut, Seq[InputMapper[In, POut]]]): Map[GOut, Seq[InputMapper[In, POut]]] = {

    @tailrec
    def reduceAndCompute(
        notLimited: Option[Map[GOut, Seq[InputMapper[In, POut]]]],
        limited: Option[Map[GOut, Seq[InputMapper[In, POut]]]] = None): Option[Map[GOut, Seq[InputMapper[In, POut]]]] = {
      if (notLimited.isEmpty || notLimited.get.size == 1) return Some(notLimited.get ++ limited.get)

      val limitAmt = limit * sumOfPackValue

      val groupSums: Map[GOut, Double] = groups.map {
        case (k: GOut, v: Seq[In]) => {
          val sumOfGroup: Double = v.map(group => packFn(group.input)).reduce[Double](_ + _)

          (k, sumOfGroup)
        }
      }

      val bucketExcessSum: Double = groupSums.filter(_._2 > limitAmt).map {
        case (_: GOut, v: Double) => v - limitAmt
      }.sum

      val updatedlimited= Some(groupSums.filter(_._2 > limitAmt).map {
        case (k, v) => {
          val amtOver = v - limitAmt
          val amtEa = amtOver / v

          (k, groups(k).map(i => i.copy(updatedPackingProp = (i.updatedPackingProp - amtEa).asInstanceOf[POut])))
        }
      })

      val updatedNotLimited = Some(groupSums.filter(_._2 <= limitAmt).map {
        case (k, v) => {
          val amtEa = bucketExcessSum / v

          (k, groups(k).map(i => i.copy(updatedPackingProp = (i.updatedPackingProp + amtEa).asInstanceOf[POut])))
        }
      })

      reduceAndCompute(updatedNotLimited, updatedlimited)
    }

    reduceAndCompute(Some(groups)).get
  }
}
