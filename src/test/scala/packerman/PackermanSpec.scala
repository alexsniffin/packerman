package packerman

import org.scalatest.{FlatSpec, GivenWhenThen}
import packerman.impl.UniformDistribution

class PackermanSpec extends FlatSpec with GivenWhenThen {
  info("Running PackermanSpec")

  case class Test(one: Double, two: String, three: String)

  "" should "" in {
    Given("")

    val packed = Packerman(List(Test(1, "ok", "no")))
      .groupBy(x => x.two)
      .packBy(x => x.one)
      .distributionStrategy(UniformDistribution(0.5))
      .compute()

    When("")

    Then("")
    assert(true)
  }
}
