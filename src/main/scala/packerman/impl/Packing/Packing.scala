package packerman.impl.Packing

import packerman.Pack
import packerman.impl.DistributionStrategy.{DistributionStrategy, DistributionStrategyMonad}

trait PackingMonad[In] {
  def packBy[POut](fn: Pack.Packing[In, POut], limit: Double): DistributionStrategyMonad[In]
}

class Packing[In](pack: Pack[In]) extends PackingMonad[In] {
  def packBy[POut](fn: Pack.Packing[In, POut], limit: Double): DistributionStrategyMonad[In] = DistributionStrategy(pack.copy(packFn = Some(fn)))
}

object Packing {
  def apply[In](pack: Pack[In]): Packing[In] = new Packing[In](pack)
}
