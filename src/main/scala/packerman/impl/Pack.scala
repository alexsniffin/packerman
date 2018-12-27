package packerman.impl

trait PackProperties[In] {
  val collection: Option[Seq[In]]
  val groupFn: Option[Pack.Grouping[In, Any]]
  val packFn: Option[Pack.Packing[In, Any]]
  val distributeFn: Option[Pack.Distribution[In]]
}

case class Pack[In](
    collection: Option[Seq[In]],
    groupFn: Option[Pack.Grouping[In, Any]] = None,
    packFn: Option[Pack.Packing[In, Any]] = None,
    distributeFn: Option[Pack.Distribution[In]] = None)
  extends PackProperties[In]

object Pack {
  type LambdaVariant[-In, +Out] = In => Out

  // Alias for different lambda operations
  type Grouping[-In, +GOut] = LambdaVariant[In, GOut]
  type Packing[-In, +POut] = LambdaVariant[In, POut]

  type Distribution[Distribute] = Distribute => Distribute

  def apply[In](inCollection: Seq[In]): Pack[In] = new Pack[In](Some(inCollection))
}

