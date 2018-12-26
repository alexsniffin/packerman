package packerman.impl.Packing

import packerman.impl.DistributionStrategy.{DistributionStrategy, DistributionStrategyMonad}
import packerman.impl.Pack

trait PackingMonad[In] {
  def packBy[POut](fn: Pack.Packing[In, POut], limit: Double): DistributionStrategyMonad[In]
}

class Packing[In](pack: Pack[In]) extends PackingMonad[In] {
  def packBy[POut](fn: Pack.Packing[In, POut], limit: Double): DistributionStrategyMonad[In] = DistributionStrategy(pack.copy(packFn = Some(fn)))
}

object Packing {
  def apply[In](pack: Pack[In]): Packing[In] = new Packing[In](pack)
}
