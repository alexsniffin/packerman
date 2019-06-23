package packerman

import packerman.impl.Group.{Group, GroupProps}
import packerman.impl.Pack

object PackermanEngine {
  def apply[In](inputCollection: Seq[In]): GroupProps[In] = {
    val input: Pack[In, _, _ <: Double] = Pack[In](inputCollection)

    Group[In](input)
  }
}