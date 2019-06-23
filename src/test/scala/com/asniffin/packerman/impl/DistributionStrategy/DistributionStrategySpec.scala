package com.asniffin.packerman.impl.DistributionStrategy

import org.scalatest.{FlatSpec, GivenWhenThen}
import com.alexsniffin.packerman.TestClass
import com.alexsniffin.packerman.impl.Computation.{Computation, UniformDistribution}
import com.alexsniffin.packerman.impl.Pack

class DistributionStrategySpec extends FlatSpec with GivenWhenThen {
  info("Running DistributionStrategySpec")

  "apply" should "return a distribution monad" in {
    Given("a pack object")
    val pack = Pack[TestClass](Seq(TestClass("Test", 0)))

    When("creating a new instance of distribution with apply")
    val distribution = DistributionStrategy.apply(pack)

    Then("apply should return a group monad")
    assert(distribution.isInstanceOf[DistributionStrategy[TestClass, _, _]])
  }

  "distributionStrategy" should "return a computation instance" in {
    Given("a distribution monad and a computation strategy")
    val distribution = DistributionStrategy(Pack[TestClass](Seq(TestClass("Test", 0))))
    val strategy: UniformDistribution = UniformDistribution(true, 1)

    When("distributionStrategy is called with the strategy")
    val computation = distribution.distributionStrategy(strategy)

    Then("it should return a computation monad")
    assert(computation.isInstanceOf[Computation[TestClass, _, _]])
  }

  "distributionStrategy" should "return a computation instance that copied the pack object and added strategy" in {
    Given("a pack object, distribution monad and strategy")
    val pack = Pack(Seq(TestClass("Test", 0)))
    val distribution = DistributionStrategy(pack)
    val strategy: UniformDistribution = UniformDistribution(true, 1)

    When("distributionStrategy is called with the strategy")
    val computation: Computation[TestClass, _, _] = distribution.distributionStrategy(strategy).asInstanceOf[Computation[TestClass, _, _]]

    Then("the pack object should be different than the original")
    assert(computation.pack != pack)
    assert(computation.pack.distributeAlgorithm.isDefined)
  }
}
