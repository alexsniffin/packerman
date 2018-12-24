package packerman.impl.Computation

import packerman.Pack
import packerman.impl.Packed.Packed

trait ComputationMonad[In] {
  def compute(): Packed[In]
}

class Computation[In, GOut, POut](In: Pack[In])(groupFn: Pack.Grouping[In, GOut], packFn: Pack.Packing[In, POut], distributeFn: Pack.Distribution[In])
  extends ComputationMonad[In] {
  def compute(): Packed[In] = ???
}

object Computation {
  def apply[In, GOut, POut](In: Pack[In])(groupFn: Pack.Grouping[In, GOut], packFn: Pack.Packing[In, POut], distributeFn: Pack.Distribution[In]):
  Computation[In, GOut, POut] = new Computation[In, GOut, POut](In)(groupFn, packFn, distributeFn)
}
