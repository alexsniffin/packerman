package packerman.impl.Packing

import packerman.Pack
import packerman.impl.DistributionStrategy.{DistributionStrategy, DistributionStrategyMonad}

trait PackingMonad[In] {
  def packBy[POut](fn: Pack.Packing[In, POut], limit: Double): DistributionStrategyMonad[In]
}

class Packing[In, GOut](In: Pack[In])(groupFn: Pack.Grouping[In, GOut]) extends PackingMonad[In] {
  def packBy[POut](fn: Pack.Packing[In, POut], limit: Double): DistributionStrategyMonad[In] = DistributionStrategy(In)(groupFn, fn)
}

object Packing {
  def apply[In, GOut](In: Pack[In])(groupFn: Pack.Grouping[In, GOut]):
  Packing[In, GOut] = new Packing[In, GOut](In)(groupFn)
}
