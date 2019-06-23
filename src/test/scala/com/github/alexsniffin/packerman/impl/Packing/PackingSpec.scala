package com.github.alexsniffin.packerman.impl.Packing

import com.github.alexsniffin.packerman.TestClass
import com.github.alexsniffin.packerman.impl.DistributionStrategy.DistributionStrategy
import com.github.alexsniffin.packerman.impl.Pack
import org.scalatest.{FlatSpec, GivenWhenThen}

class PackingSpec extends FlatSpec with GivenWhenThen {
  info("Running PackingSpec")

  "apply" should "return a packing monad" in {
    Given("a pack object")
    val pack = Pack[TestClass](Seq(TestClass("Test", 0)))

    When("creating a new instance of group with apply")
    val packing = Packing.apply(pack)

    Then("apply should return a group monad")
    assert(packing.isInstanceOf[Packing[TestClass, _]])
  }

  "packBy" should "return a distribution instance" in {
    Given("a group monad and fn with covariant string")
    val packing = Packing(Pack[TestClass](Seq(TestClass("Test", 0))))
    val fn: TestClass => Double = testClass => testClass.num

    When("packBy is called with the fn")
    val distribution = packing.packBy(fn)

    Then("it should return a distribution monad")
    assert(distribution.isInstanceOf[DistributionStrategy[TestClass, _, Double]])
  }

  "packBy" should "return a distribution instance that copied the pack object and added a fn" in {
    Given("a pack object, group monad and fn")
    val pack = Pack(Seq(TestClass("Test", 0)))
    val packing = Packing(pack)
    val fn: TestClass => Double = testClass => testClass.num

    When("groupBy is called with a fn")
    val distribution: DistributionStrategy[TestClass, _, Double] = packing.packBy(fn).asInstanceOf[DistributionStrategy[TestClass, _, Double]]

    Then("the pack object should be different than the original")
    assert(distribution.pack != pack)
    assert(distribution.pack.packFn.isDefined)
  }
}

