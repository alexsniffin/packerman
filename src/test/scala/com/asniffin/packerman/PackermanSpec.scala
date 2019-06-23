package com.asniffin.packerman

import org.scalatest.{FlatSpec, GivenWhenThen}
import com.asniffin.packerman.impl.Group.GroupProps

class PackermanSpec extends FlatSpec with GivenWhenThen {
  info("Running PackermanSpec")

  "apply" should "return a group monad" in {
    Given("a seq")
    val seq = Seq()

    When("creating a new instance of com.alexsniffin.packerman with apply")
    val groupMonad = PackermanEngine.apply(seq)

    Then("apply should return a group monad")
    assert(groupMonad.isInstanceOf[GroupProps[_]])
  }
}
