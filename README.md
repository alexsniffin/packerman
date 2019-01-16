# packerman
This is a simple library for basic and general-purpose bin-packing functionality.

## Usage
The design of this library allows you to customize your usage against how to want to bin-pack your collection of data.

### Example
```scala
case class Data(block: String, bytes: Int)

val collection = Seq(Data("0x1", 588665550), Data("0x1", 1045661516), Data("0x2", 232359110))

val packerman = Packerman(collection)
  .groupBy(x => x.block)
  .packBy(x => x.bytes)
  .distributionStrategy(UniformDistribution(true, .5))
  .compute()

val result = packerman match {
  case Left(data) => { data }
  case Right(err) => throw new Exception(err.reason)
}

```
