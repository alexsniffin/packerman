package com.github.alexsniffin.packerman.impl.DistributionStrategy

import com.github.alexsniffin.packerman.TestClass
import com.github.alexsniffin.packerman.impl.Computation.{Computation, UniformDistribution}
import com.github.alexsniffin.packerman.impl.Pack
import org.scalatest.{FlatSpec, GivenWhenThen}

import scala.math.Numeric.IntIsIntegral

class DistributionStrategySpec extends FlatSpec with GivenWhenThen {
  info("Running DistributionStrategySpec")

  "apply" should "return a distribution monad" in {
    Given("a pack object")
    val pack = Pack.create[TestClass, Any, Int](Some(Seq(TestClass("Test", 0))), None, None)

    When("creating a new instance of distribution with apply")
    val distribution = DistributionStrategy.apply[TestClass, Any, Int](pack)

    Then("apply should return a group monad")
    assert(distribution.isInstanceOf[DistributionStrategy[TestClass, _, _]])
  }

  "distributionStrategy" should "return a computation instance" in {
    Given("a distribution monad and a computation strategy")
    val distribution = DistributionStrategy(Pack.create[TestClass, Any, Int](Some(Seq(TestClass("Test", 0))), None, None))
    val strategy: UniformDistribution = UniformDistribution(true, 1)

    When("distributionStrategy is called with the strategy")
    val computation = distribution.distributionStrategy(strategy)

    Then("it should return a computation monad")
    assert(computation.isInstanceOf[Computation[TestClass, _, _]])
  }

  "distributionStrategy" should "return a computation instance that copied the pack object and added strategy" in {
    Given("a pack object, distribution monad and strategy")
    val pack = Pack.create[TestClass, Any, Int](Some(Seq(TestClass("Test", 0))), None, None)
    val distribution = DistributionStrategy(pack)
    val strategy: UniformDistribution = UniformDistribution(true, 1)

    When("distributionStrategy is called with the strategy")
    val computation: Computation[TestClass, _, _] = distribution.distributionStrategy(strategy).asInstanceOf[Computation[TestClass, _, _]]

    Then("the pack object should be different than the original")
    assert(computation.pack != pack)
    assert(computation.pack.distributeAlgorithm.isDefined)
  }
}
