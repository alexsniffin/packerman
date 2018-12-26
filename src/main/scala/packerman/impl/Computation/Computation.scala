package packerman.impl.Computation

import packerman.Pack
import packerman.impl.Packed.Packed

trait ComputationMonad[In] {
  def compute(): Packed[In]
}

class Computation[In](pack: Pack[In]) extends ComputationMonad[In] {
  def compute(): Packed[In] = ???
}

object Computation {
  def apply[In](pack: Pack[In]): Computation[In] = new Computation[In](pack)
}
