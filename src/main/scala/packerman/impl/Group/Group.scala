package packerman.impl.Group

import packerman.Pack
import packerman.impl.Packing.{Packing, PackingMonad}

trait GroupMonad[In] {
  def groupBy[GOut](fn: Pack.Grouping[In, GOut]): PackingMonad[In]
}

class Group[In](pack: Pack[In]) extends GroupMonad[In] {
  def groupBy[GOut](fn: Pack.Grouping[In, GOut]): PackingMonad[In] = Packing(pack.copy(groupFn = Some(fn)))
}

object Group {
  def apply[In](pack: Pack[In]): Group[In] = new Group[In](pack)
}