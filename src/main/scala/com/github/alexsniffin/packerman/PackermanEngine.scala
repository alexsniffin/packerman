package com.github.alexsniffin.packerman

import com.github.alexsniffin.packerman.impl.Group.{Group, GroupProps}
import com.github.alexsniffin.packerman.impl.Pack

object PackermanEngine {
  def apply[In](inputCollection: Seq[In]): GroupProps[In] = {
    val input: Pack[In, _, _ <: Double] = Pack[In](inputCollection)

    Group[In](input)
  }
}