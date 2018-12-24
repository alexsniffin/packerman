package packerman

import packerman.impl.Group.{Group, GroupMonad}

trait PackProperties[In] {
  val collection: Seq[In]
}

class Pack[In](inputCollection: Seq[In]) extends PackProperties[In] {
  lazy val collection: Seq[In] = inputCollection
}

object Pack {
  type LambdaVariant[-In, +Out] = In => Out
  
  // Alias for different lambda operations
  type Grouping[-In, +GOut] = LambdaVariant[In, GOut]
  type Packing[-In, +POut] = LambdaVariant[In, POut]
  
  type Distribution[Distribute] = Distribute => Distribute

  def apply[In](InCollection: Seq[In]): Pack[In] = new Pack[In](InCollection)
}

object Packerman {
  def apply[In](inputCollection: Seq[In]): GroupMonad[In] = {
    val input: Pack[In] = new Pack[In](inputCollection)

    Group[In](input)
  }
}