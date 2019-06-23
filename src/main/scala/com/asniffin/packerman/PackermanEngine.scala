package com.asniffin.packerman

import com.alexsniffin.packerman.impl.Group.{Group, GroupProps}
import com.alexsniffin.packerman.impl.Pack
import com.asniffin.packerman.impl.Group.{Group, GroupProps}
import com.asniffin.packerman.impl.Pack

object PackermanEngine {
  def apply[In](inputCollection: Seq[In]): GroupProps[In] = {
    val input: Pack[In, _, _ <: Double] = Pack[In](inputCollection)

    Group[In](input)
  }
}