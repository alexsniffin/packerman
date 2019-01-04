package packerman.impl

trait PackProperties[In] {
  val collection: Option[Seq[In]]
  val groupFn: Option[Pack.Grouping[In, _]]
  val packFn: Option[Pack.Packing[In, _]]
  val distributeFn: Option[Pack.Distribution[In]]
}

case class Pack[In, GOut, POut <: Double](
    collection: Option[Seq[In]],
    groupFn: Option[Pack.Grouping[In, GOut]] = None,
    packFn: Option[Pack.Packing[In, POut]] = None,
    distributeFn: Option[Pack.Distribution[In]] = None)
  extends PackProperties[In]

object Pack {
  type LambdaVariant[-In, +Out] = In => Out

  // Alias for different lambda operations
  type Grouping[-In, +GOut] = LambdaVariant[In, GOut]
  type Packing[-In, +POut] = LambdaVariant[In, POut]

  type Distribution[Distribute] = Distribute => Distribute

  def apply[In](inCollection: Seq[In]): Pack[In, _, _ <: Double] = Pack[In, Any, Double](Some(inCollection))
}

