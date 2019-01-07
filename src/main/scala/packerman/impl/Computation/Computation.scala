package packerman.impl.Computation

import packerman.impl._

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

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

  def maxBinPacking[In, GOut, POut](
      seq: Seq[In],
      groupFn: Pack.Grouping[In, GOut],
      packFn: Pack.Packing[In, POut],
      distributeAlgorithm: DistributionAlgorithm): Seq[In] = {
    val sumOfPackValue = seq.map(packFn).reduce[Double](_ + _)

    val groups: Map[GOut, Seq[In]] = seq.groupBy(groupFn)

    val groupRatios = groups.map {
      case (k: GOut, v: Seq[In]) => (k, v.map(packFn).reduce[Double](_ + _) / sumOfPackValue)
    }

    val distributed = distributeAlgorithm match {
      case UniformDistribution(weighted, limit) => uniformDistribution(weighted, limit, sumOfPackValue, groups, groupRatios)
    }

    distributed.values.flatten.toSeq
  }

  def uniformDistribution[In, GOut](
      weighted: Boolean,
      limit: Double,
      sumOfPackValue: Double,
      groups: Map[GOut, Seq[In]],
      groupRatios: Map[GOut, Double]): Map[GOut, Seq[In]] = {
    @tailrec
    def reduceAndCompute(notLimited: Option[Map[GOut, Seq[In]]], limited: Option[Map[GOut, Seq[In]]] = None): Option[Map[GOut, Seq[In]]] = {
      if (notLimited.isEmpty) return limited

      // todo

      reduceAndCompute()
    }

    reduceAndCompute(Some(groups)).get
  }
}
