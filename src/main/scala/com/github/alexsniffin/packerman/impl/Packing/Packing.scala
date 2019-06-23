package com.github.alexsniffin.packerman.impl.Packing

import com.github.alexsniffin.packerman.impl.DistributionStrategy._
import com.github.alexsniffin.packerman.impl.Pack

trait PackingProps[In] {
  def packBy[POut](fn: Pack.Packing[In, POut])(implicit n: Numeric[POut]): DistributionStrategyProps[In, POut]
}

class Packing[In, GOut](val pack: Pack[In, GOut, _]) extends PackingProps[In] {
  def packBy[POut](fn: Pack.Packing[In, POut])(implicit n: Numeric[POut]): DistributionStrategyProps[In, POut] = {
    val updated: Pack[In, GOut, POut] = Pack.create[In, GOut, POut](pack.collection, pack.groupFn, Some(fn))
    DistributionStrategy(updated)
  }
}

object Packing {
  def apply[In, GOut](pack: Pack[In, GOut, _]): Packing[In, GOut] = new Packing[In, GOut](pack)
}
