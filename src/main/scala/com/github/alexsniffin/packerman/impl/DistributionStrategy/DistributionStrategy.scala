package com.github.alexsniffin.packerman.impl.DistributionStrategy

import com.github.alexsniffin.packerman.impl.Computation.{Computation, ComputationProps, DistributionAlgorithm}
import com.github.alexsniffin.packerman.impl.Pack

trait DistributionStrategyProps[In, POut] {
  def distributionStrategy(algorithm: DistributionAlgorithm): ComputationProps[In, POut]
}

class DistributionStrategy[In, GOut, POut](val pack: Pack[In, GOut, POut])(implicit n: Numeric[POut]) extends DistributionStrategyProps[In, POut] {
  def distributionStrategy(algorithm: DistributionAlgorithm): ComputationProps[In, POut] = {
    val updated: Pack[In, GOut, POut] = Pack.create[In, GOut, POut](pack.collection, pack.groupFn, pack.packFn, Some(algorithm))
    Computation[In, GOut, POut](updated)
  }
}

object DistributionStrategy {
  def apply[In, GOut, POut](pack: Pack[In, GOut, POut])(implicit n: Numeric[POut]): DistributionStrategy[In, GOut, POut] = new DistributionStrategy[In, GOut, POut](pack)
}
