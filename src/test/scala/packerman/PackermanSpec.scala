package packerman

import org.scalatest.{FlatSpec, GivenWhenThen}

class PackermanSpec extends FlatSpec with GivenWhenThen {
  info("Running PackermanSpec")

  case class Test(one: Int, two: String, three: String)

  "" should "" in {
    Given("")

    val packed = Packerman(List(Test(1, "ok", "no")))
      .groupBy(x => x.two)
      .packBy(x => x.one, 0.1)
      .distributionStrategy(x => x)
      .compute()
      .result

    When("")

    Then("")
    assert(true)
  }
}
