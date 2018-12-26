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
      case Pack(_, groupFn, packFn, distributeFn) if groupFn.isEmpty || packFn.isEmpty || distributeFn.isEmpty => Right(MissingParametersError("Required parameters are missing from pack"))
      case Pack(input, groupFn, packFn, distributeFn) => Left(input.get) // todo
      case _ => Right(UnknownError("Unknown error"))
  }
}

object Computation {
  def apply[In](pack: Pack[In]): Computation[In] = new Computation[In](pack)
}
