package packerman.impl.Computation

import packerman.impl.Pack

import scala.util.{Failure, Success, Try}

sealed class Error(reason: String)
case class ThrowableError(reason: String) extends Error(reason: String)
case class UnknownError(reason: String) extends Error(reason: String)
case class MissingParametersError(reason: String) extends Error(reason: String)

trait ComputationMonad[In] {
  def compute(): Either[Seq[In], Error]
}

class Computation[In, GOut, POut <: Double](pack: Pack[In, GOut, POut]) extends ComputationMonad[In] {
  def compute(): Either[Seq[In], Error] = pack match {
    case Pack(_, groupFn, packFn, distributeFn) if groupFn.isEmpty || packFn.isEmpty || distributeFn.isEmpty =>
      Right(MissingParametersError("Required parameters are missing from pack"))
    case Pack(input, groupFn, packFn, distributeFn) => Try(maxBinPacking(input.get, groupFn.get, packFn.get, distributeFn.get)) match {
      case Success(result: Seq[In]) => Left(result)
      case Failure(fail) => Right(ThrowableError(fail.getMessage))
    }
    case _ => Right(UnknownError("Unknown error"))
  }

  def maxBinPacking(seq: Seq[In], groupFn: Pack.Grouping[In, GOut], packFn: Pack.Packing[In, POut],
                    distributeFn: Pack.Distribution[In]): Seq[In] = {
    val sumOfPackValue = seq.map(packFn).reduce[Double](_ + _)

    val grouped: Map[GOut, Seq[In]] = seq.groupBy(groupFn)

    val groupRatios = grouped.map {
      case (k: GOut, v: Seq[In]) => {
        val sumOfGroup = v.map(packFn).reduce[Double](_ + _)
        (k, sumOfGroup / sumOfPackValue)
      }
    }

    //val distributed = //packed.map(distributeFn)

    List[In]()
  }
}

object Computation {
  def apply[In, GOut, POut <: Double](pack: Pack[In, GOut, POut]): Computation[In, GOut, POut] = new Computation[In, GOut, POut](pack)
}
