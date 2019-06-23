package com.github.alexsniffin.packerman.impl.Group

import com.github.alexsniffin.packerman.impl.Pack
import com.github.alexsniffin.packerman.impl.Packing.{Packing, PackingProps}


trait GroupProps[In] {
  def groupBy[GOut](fn: Pack.Grouping[In, GOut]): PackingProps[In]
}

class Group[In](val pack: Pack[In, _, _]) extends GroupProps[In] {
  def groupBy[GOut](fn: Pack.Grouping[In, GOut]): PackingProps[In] = {
    val updated: Pack[In, GOut, _] = Pack.create[In, GOut](pack.collection, Some(fn))
    Packing(updated)
  }
}

object Group {
  def apply[In](pack: Pack[In, _, _]): Group[In] = new Group[In](pack)
}