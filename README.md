# packerman
This is a lightweight library for basic and general-purpose bin-packing of a collection with arbitrary values.

## Usage
The design of this library allows you to define your usage on how to bin-pack your input collection.

### Example
```scala
case class Input(key: String, value: Int)

val collection = Seq(Input("29b482bca2af868b0cc6c7c409fe3d14", 588665550), Input("d724b748d259fa0f39a4ac776a86ad61", 1045661516), Input("df1662f0aed98c78c192654461afb6f6", 232359110))

val packerman = PackermanEngine(collection)
  .groupBy(x => x.key)
  .packBy(x => x.value)
  .distributionStrategy(UniformDistribution(true, .5))
  .compute()

val output = packerman match {
  case Right(result) => result
  case Left(err) => throw new Exception(err.reason)
}

```
