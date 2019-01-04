package packerman.impl.Packing

import packerman.impl.DistributionStrategy.{DistributionStrategy, DistributionStrategyMonad}
import packerman.impl.Pack

trait PackingMonad[In] {
  def packBy[POut <: Double](fn: Pack.Packing[In, POut], limit: Double): DistributionStrategyMonad[In]
}

class Packing[In, GOut](pack: Pack[In, GOut, _ <: Double]) extends PackingMonad[In] {
  def packBy[POut <: Double](fn: Pack.Packing[In, POut], limit: Double): DistributionStrategyMonad[In] =
    DistributionStrategy(pack.copy[In, GOut, POut](packFn = Some(fn)))
}

object Packing {
  def apply[In, GOut](pack: Pack[In, GOut, _ <: Double]): Packing[In, GOut] = new Packing[In, GOut](pack)
}
