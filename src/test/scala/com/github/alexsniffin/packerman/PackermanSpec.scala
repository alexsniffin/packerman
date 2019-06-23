package com.github.alexsniffin.packerman

import com.github.alexsniffin.packerman.impl.Computation.UniformDistribution
import org.scalatest.{FlatSpec, GivenWhenThen}
import com.github.alexsniffin.packerman.impl.Group.GroupProps

class PackermanSpec extends FlatSpec with GivenWhenThen {
  info("Running PackermanSpec")

  "apply" should "return a group monad" in {
    case class Input(key: String, value: Int)

    val collection = Seq(
      Input("29b482bca2af868b0cc6c7c409fe3d14", 11),
      Input("d724b748d259fa0f39a4ac776a86ad61", 2),
      Input("df1662f0aed98c78c192654461afb6f6", 2)
    )

    val packerman = PackermanEngine(collection)
        .groupBy(x => x.key)
        .packBy(x => x.value)
        .distributionStrategy(UniformDistribution(true, .5))
        .compute()

    val output = packerman match {
      case Right(result) => result
      case l @ Left(err) => l // handle error
    }
//    Given("a seq")
//    val seq = Seq()
//
//    When("creating a new instance of com.alexsniffin.packerman with apply")
//    val groupMonad = PackermanEngine.apply(seq)
//
//    Then("apply should return a group monad")
//    assert(groupMonad.isInstanceOf[GroupProps[_]])
  }
}
