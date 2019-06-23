package com.asniffin.packerman.impl.Group

import com.asniffin.packerman.TestClass
import com.asniffin.packerman.impl.Pack
import org.scalatest.{FlatSpec, GivenWhenThen}
import com.asniffin.packerman.impl.Packing.Packing

class GroupSpec extends FlatSpec with GivenWhenThen {
  info("Running GroupSpec")

  "apply" should "return a group monad" in {
    Given("a pack object")
    val pack = Pack[TestClass](Seq(TestClass("Test", 0)))

    When("creating a new instance of group with apply")
    val group = Group.apply(pack)

    Then("apply should return a group monad")
    assert(group.isInstanceOf[Group[TestClass]])
  }

  "groupBy" should "return a packing instance" in {
    Given("a group monad and fn with covariant string")
    val group = Group(Pack[TestClass](Seq(TestClass("Test", 0))))
    val fn: TestClass => String = testClass => testClass.str

    When("groupBy is called with the fn")
    val packing = group.groupBy(fn)

    Then("it should return a packing monad")
    assert(packing.isInstanceOf[Packing[TestClass, String]])
  }

  "groupBy" should "return a packing instance that copied the pack object and added a fn" in {
    Given("a pack object, group monad and fn")
    val pack = Pack(Seq(TestClass("Test", 0)))
    val group = Group(pack)
    val fn: TestClass => String = testClass => testClass.str

    When("groupBy is called with a fn")
    val packing: Packing[TestClass, String] = group.groupBy(fn).asInstanceOf[Packing[TestClass, String]]

    Then("the pack object should be different than the original")
    assert(packing.pack != pack)
    assert(packing.pack.groupFn.isDefined)
  }
}
