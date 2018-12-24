package packerman.impl.DistributionStrategy

import packerman.Pack
import packerman.impl.Computation.{Computation, ComputationMonad}

trait DistributionStrategyMonad[In] {
  def distributionStrategy(fn: Pack.Distribution[In]): ComputationMonad[In]
}

class DistributionStrategy[In, GOut, POut](In: Pack[In])(groupFn: Pack.Grouping[In, GOut], packFn: Pack.Packing[In, POut]) extends DistributionStrategyMonad[In] {
  def distributionStrategy(fn: Pack.Distribution[In]): ComputationMonad[In] = Computation[In, GOut, POut](In)(groupFn, packFn, fn)
}

object DistributionStrategy {
  def apply[In, GOut, POut](In: Pack[In])(groupFn: Pack.Grouping[In, GOut], packFn: Pack.Packing[In, POut]): DistributionStrategy[In, GOut, POut] =
    new DistributionStrategy[In, GOut, POut](In)(groupFn, packFn)
}
