package packerman.impl.DistributionStrategy

import packerman.impl.Computation.{Computation, ComputationMonad}
import packerman.impl.{DistributionAlgorithm, Pack}

trait DistributionStrategyMonad[In] {
  def distributionStrategy(algorithm: DistributionAlgorithm): ComputationMonad[In]
}

class DistributionStrategy[In, GOut, POut <: Double](val pack: Pack[In, GOut, POut]) extends DistributionStrategyMonad[In] {
  def distributionStrategy(algorithm: DistributionAlgorithm): ComputationMonad[In] =
    Computation[In, GOut, POut](pack.copy[In, GOut, POut](distributeAlgorithm = Some(algorithm)))
}

object DistributionStrategy {
  def apply[In, GOut, POut <: Double](pack: Pack[In, GOut, POut]): DistributionStrategy[In, GOut, POut] = new DistributionStrategy[In, GOut, POut](pack)
}
