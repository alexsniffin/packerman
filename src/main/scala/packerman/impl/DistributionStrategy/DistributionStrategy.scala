package packerman.impl.DistributionStrategy

import packerman.Pack
import packerman.impl.Computation.{Computation, ComputationMonad}

trait DistributionStrategyMonad[In] {
  def distributionStrategy(fn: Pack.Distribution[In]): ComputationMonad[In]
}

class DistributionStrategy[In](pack: Pack[In]) extends DistributionStrategyMonad[In] {
  def distributionStrategy(fn: Pack.Distribution[In]): ComputationMonad[In] = Computation[In](pack.copy(distributeFn = Some(fn)))
}

object DistributionStrategy {
  def apply[In](pack: Pack[In]): DistributionStrategy[In] = new DistributionStrategy[In](pack)
}
