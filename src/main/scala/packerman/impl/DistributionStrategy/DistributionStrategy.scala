package packerman.impl.DistributionStrategy

import packerman.impl.Computation.{Computation, ComputationProps, DistributionAlgorithm}
import packerman.impl.Pack

trait DistributionStrategyProps[In, POut <: Double] {
  def distributionStrategy(algorithm: DistributionAlgorithm): ComputationProps[In, POut]
}

class DistributionStrategy[In, GOut, POut <: Double](val pack: Pack[In, GOut, POut]) extends DistributionStrategyProps[In, POut] {
  def distributionStrategy(algorithm: DistributionAlgorithm): ComputationProps[In, POut] =
    Computation[In, GOut, POut](pack.copy[In, GOut, POut](distributeAlgorithm = Some(algorithm)))
}

object DistributionStrategy {
  def apply[In, GOut, POut <: Double](pack: Pack[In, GOut, POut]): DistributionStrategy[In, GOut, POut] = new DistributionStrategy[In, GOut, POut](pack)
}
