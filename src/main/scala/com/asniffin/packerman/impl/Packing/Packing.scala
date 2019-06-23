package com.asniffin.packerman.impl.Packing

import com.alexsniffin.packerman.impl.DistributionStrategy.{DistributionStrategy, DistributionStrategyProps}
import com.alexsniffin.packerman.impl.Pack
import com.asniffin.packerman.impl.DistributionStrategy.DistributionStrategyProps

trait PackingProps[In] {
  def packBy[POut <: Double](fn: Pack.Packing[In, POut]): DistributionStrategyProps[In, POut]
}

class Packing[In, GOut](val pack: Pack[In, GOut, _ <: Double]) extends PackingProps[In] {
  def packBy[POut <: Double](fn: Pack.Packing[In, POut]): DistributionStrategyProps[In, POut] =
    DistributionStrategy(pack.copy[In, GOut, POut](packFn = Some(fn)))
}

object Packing {
  def apply[In, GOut](pack: Pack[In, GOut, _ <: Double]): Packing[In, GOut] = new Packing[In, GOut](pack)
}