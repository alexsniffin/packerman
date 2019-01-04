package packerman.impl.Group

import packerman.impl.Pack
import packerman.impl.Packing.{Packing, PackingMonad}

trait GroupMonad[In] {
  def groupBy[GOut](fn: Pack.Grouping[In, GOut]): PackingMonad[In]
}

class Group[In](pack: Pack[In, _, _ <: Double]) extends GroupMonad[In] {
  def groupBy[GOut](fn: Pack.Grouping[In, GOut]): PackingMonad[In] =
    Packing(pack.copy[In, GOut, Double](groupFn = Some(fn)))
}

object Group {
  def apply[In](pack: Pack[In, _, _ <: Double]): Group[In] = new Group[In](pack)
}