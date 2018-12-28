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

class Computation[In](pack: Pack[In]) extends ComputationMonad[In] {
  def compute(): Either[Seq[In], Error] = pack match {
    case Pack(_, groupFn, packFn, distributeFn) if groupFn.isEmpty || packFn.isEmpty || distributeFn.isEmpty =>
      Right(MissingParametersError("Required parameters are missing from pack"))
    case Pack(input, groupFn, packFn, distributeFn) => Try(maxBinPacking(input.get, groupFn.get, packFn.get, distributeFn.get)) match {
      case Success(result: Seq[In]) => Left(result)
      case Failure(fail) => Right(ThrowableError(fail.getMessage))
    }
    case _ => Right(UnknownError("Unknown error"))
  }

  def maxBinPacking(seq: Seq[In], groupFn: Pack.Grouping[In, Any], packFn: Pack.Packing[In, Any],
                    distributeFn: Pack.Distribution[In]): Seq[In] = {
    val sumOfPackValue: Double = sumNumericToDouble(seq.map(packFn))

    val grouped: Map[Any, Seq[In]] = seq.groupBy(groupFn)

    val groupRatios = grouped.map {
      case (k: Any, v: Seq[In]) => {
        val sumOfGroup = sumNumericToDouble(v.map(packFn))
        (k, sumOfGroup / sumOfPackValue)
      }
    }

    //val distributed = //packed.map(distributeFn)

    List[In]()
  }

  def sumNumericToDouble(seq: Seq[Any]): Double = seq.map {
    case i: Int => i.intValue()
    case i: Double => i.doubleValue()
    case i: Long => i.longValue()
    case i: Short => i.shortValue()
    case i: Float => i.floatValue()
    //case _ => throw new NumberFormatException("Invalid type for packing, must be a numeric type")
  }.sum
}

object Computation {
  def apply[In](pack: Pack[In]): Computation[In] = new Computation[In](pack)
}
