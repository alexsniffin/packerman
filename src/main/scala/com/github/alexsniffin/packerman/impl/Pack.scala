package com.github.alexsniffin.packerman.impl

import com.github.alexsniffin.packerman.impl.Computation.DistributionAlgorithm

trait PackProps[In, GOut, POut] {
  val collection: Option[Seq[In]]
  val groupFn: Option[Pack.Grouping[In, GOut]]
  val packFn: Option[Pack.Packing[In, POut]]
  val distributeAlgorithm: Option[DistributionAlgorithm]
}

case class Pack[In, GOut, POut : Numeric](
    collection: Option[Seq[In]],
    groupFn: Option[Pack.Grouping[In, GOut]] = None,
    packFn: Option[Pack.Packing[In, POut]] = None,
    distributeAlgorithm: Option[DistributionAlgorithm] = None) extends PackProps[In, GOut, POut]

object Pack {

  type Grouping[-In, +GOut] = In => GOut
  type Packing[-In, +POut] = In => POut

  def create[In](
      inCollection: Option[Seq[In]]): Pack[In, _, _] =
    Pack[In, Any, Double](inCollection, None, None, None)

  def create[In, GOut](
      inCollection: Option[Seq[In]],
      inGroupFn: Option[Pack.Grouping[In, GOut]]): Pack[In, GOut, _] =
    Pack[In, GOut, Double](inCollection, inGroupFn, None, None)

  def create[In, GOut, POut : Numeric](
      inCollection: Option[Seq[In]],
      inGroupFn: Option[Pack.Grouping[In, GOut]],
      inPackFn: Option[Pack.Packing[In, POut]]): Pack[In, GOut, POut] =
    Pack[In, GOut, POut](inCollection, inGroupFn, inPackFn, None)

  def create[In, GOut, POut : Numeric](
      inCollection: Option[Seq[In]],
      inGroupFn: Option[Pack.Grouping[In, GOut]],
      inPackFn: Option[Pack.Packing[In, POut]],
      inDistributeAlgorithm: Option[DistributionAlgorithm]): Pack[In, GOut, POut] =
    Pack[In, GOut, POut](inCollection, inGroupFn, inPackFn, inDistributeAlgorithm)

}

