package packerman.impl

import packerman.impl.Computation.DistributionAlgorithm

trait PackProps[In] {
  val collection: Option[Seq[In]]
  val groupFn: Option[Pack.Grouping[In, _]]
  val packFn: Option[Pack.Packing[In, _]]
  val distributeAlgorithm: Option[DistributionAlgorithm]
}

case class Pack[In, GOut, POut <: Double](
    collection: Option[Seq[In]],
    groupFn: Option[Pack.Grouping[In, GOut]] = None,
    packFn: Option[Pack.Packing[In, POut]] = None,
    distributeAlgorithm: Option[DistributionAlgorithm] = None)
    extends PackProps[In]

object Pack {
  type LambdaVariant[-In, +Out] = In => Out

  // Alias for different lambda operations
  type Grouping[-In, +GOut] = LambdaVariant[In, GOut]
  type Packing[-In, +POut] = LambdaVariant[In, POut]

  def apply[In](inCollection: Seq[In]): Pack[In, _, _ <: Double] = Pack[In, Any, Double](Some(inCollection))
}

