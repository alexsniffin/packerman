package com.github.alexsniffin.packerman

import com.github.alexsniffin.packerman.impl.Group.GroupProps
import org.scalatest.{FlatSpec, GivenWhenThen}

class PackermanSpec extends FlatSpec with GivenWhenThen {
  info("Running PackermanSpec")

  "apply" should "return a group monad" in {
    Given("a seq")
    val seq = Seq()

    When("creating a new instance of packerman with apply")
    val groupMonad = PackermanEngine.apply(seq)

    Then("apply should return a group monad")
    assert(groupMonad.isInstanceOf[GroupProps[_]])
  }
}
