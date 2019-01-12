package packerman

import org.scalatest.{FlatSpec, GivenWhenThen}
import packerman.impl.UniformDistribution

class PackermanSpec extends FlatSpec with GivenWhenThen {
  info("Running PackermanSpec")

  case class Test(one: Double, two: String)

  "" should "" in {
    Given("")

    val packed = Packerman(List(Test(1, "group1"), Test(1, "group1"), Test(1, "group2")))
      .groupBy(x => x.two)
      //.mapInputSetBy((map, input) => map(input.one))
      .packBy(x => x.one)
      .distributionStrategy(UniformDistribution(weighted = true, 0.5))
      .compute()

    When("")

    Then("")
    assert(true)
  }
}
