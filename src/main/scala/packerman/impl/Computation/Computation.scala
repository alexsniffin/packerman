package packerman.impl.Computation

import packerman.impl.Pack

sealed class Error(reason: String)
case class UnknownError(reason: String) extends Error(reason: String)
case class MissingParametersError(reason: String) extends Error(reason: String)

trait ComputationMonad[In] {
  def compute(): Either[Seq[In], Error]
}

class Computation[In](pack: Pack[In]) extends ComputationMonad[In] {
  def compute(): Either[Seq[In], Error] = pack match {
    case Pack(_, groupFn, packFn, distributeFn) if groupFn.isEmpty || packFn.isEmpty || distributeFn.isEmpty =>
      Right(MissingParametersError("Required parameters are missing from pack"))
    case Pack(input, groupFn, packFn, distributeFn) => Left(maxBinPacking(input.get, groupFn.get, packFn.get, distributeFn.get))
    case _ => Right(UnknownError("Unknown error"))
  }

  def maxBinPacking(seq: Seq[In], groupFn: Pack.Grouping[In, Any], packFn: Pack.Packing[In, Any],
                    distributeFn: Pack.Distribution[In]): Seq[In] = {
    val sumOfPackValue = sumNumericToDouble(seq.map(packFn))

    val grouped = seq.groupBy(groupFn)

    val packed = grouped.foreach(x => {
      val sumOfGroup = sumNumericToDouble(x._2)

      //val
    })

    //val distributed = //packed.map(distributeFn)

    List[In]()
  }

  def sumNumericToDouble(seq: Seq[Any]): Double = seq.map {
    case i: Int => i.intValue()
    case i: Double => i.doubleValue()
    case i: Long => i.longValue()
    case i: Short => i.shortValue()
    case i: Float => i.floatValue()
  }.sum
}

object Computation {
  def apply[In](pack: Pack[In]): Computation[In] = new Computation[In](pack)
}
