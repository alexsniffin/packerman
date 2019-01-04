package packerman

import packerman.impl.Group.{Group, GroupMonad}
import packerman.impl.Pack

object Packerman {
  def apply[In](inputCollection: Seq[In]): GroupMonad[In] = {
    val input: Pack[In, _, _ <: Double] = Pack[In](inputCollection)

    Group[In](input)
  }
}