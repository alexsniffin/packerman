package packerman.impl.Group

import packerman.Pack
import packerman.impl.Packing.{Packing, PackingMonad}

trait GroupMonad[In] {
  def groupBy[OutputType](fn: Pack.Grouping[In, OutputType]): PackingMonad[In]
}

class Group[In](In: Pack[In]) extends GroupMonad[In] {
  def groupBy[OutputType](fn: Pack.Grouping[In, OutputType]): PackingMonad[In] = Packing(In)(fn)
}

object Group {
  def apply[In](In: Pack[In]): Group[In] = new Group[In](In)
}