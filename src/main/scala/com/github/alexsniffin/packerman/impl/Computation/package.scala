package com.github.alexsniffin.packerman.impl

package object Computation {

  sealed trait DistributionAlgorithm {
    val weighted: Boolean
  }

  case class UniformDistribution(weighted: Boolean, limit: Double) extends DistributionAlgorithm

}
