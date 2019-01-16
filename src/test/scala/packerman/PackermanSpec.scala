package packerman

import org.scalatest.{FlatSpec, GivenWhenThen}
import packerman.impl.Group.{Group, GroupMonad}

class PackermanSpec extends FlatSpec with GivenWhenThen {
  info("Running PackermanSpec")

  "apply" should "return a group monad" in {
    Given("a seq")
    val seq = Seq()

    When("creating a new instance of packerman with apply")
    val groupMonad = Packerman.apply(seq)

    Then("apply should return a group monad")
    assert(groupMonad.isInstanceOf[GroupMonad[_]])
  }
}
