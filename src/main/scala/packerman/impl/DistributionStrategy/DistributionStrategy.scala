package packerman.impl.DistributionStrategy

import packerman.impl.Computation.{Computation, ComputationMonad}
import packerman.impl.Pack

trait DistributionStrategyMonad[In] {
  def distributionStrategy(fn: Pack.Distribution[In]): ComputationMonad[In]
}

class DistributionStrategy[In, GOut, POut <: Double](pack: Pack[In, GOut, POut]) extends DistributionStrategyMonad[In] {
  def distributionStrategy(fn: Pack.Distribution[In]): ComputationMonad[In] =
    Computation[In, GOut, POut](pack.copy[In, GOut, POut](distributeFn = Some(fn)))
}

object DistributionStrategy {
  def apply[In, GOut, POut <: Double](pack: Pack[In, GOut, POut]): DistributionStrategy[In, GOut, POut] = new DistributionStrategy[In, GOut, POut](pack)
}
