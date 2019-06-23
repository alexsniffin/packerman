package packerman.impl.Group

import packerman.impl.Pack
import packerman.impl.Packing.{Packing, PackingProps}

trait GroupProps[In] {
  def groupBy[GOut](fn: Pack.Grouping[In, GOut]): PackingProps[In]
}

class Group[In](val pack: Pack[In, _, _ <: Double]) extends GroupProps[In] {
  def groupBy[GOut](fn: Pack.Grouping[In, GOut]): PackingProps[In] =
    Packing(pack.copy[In, GOut, Double](groupFn = Some(fn)))
}

object Group {
  def apply[In](pack: Pack[In, _, _ <: Double]): Group[In] = new Group[In](pack)
}