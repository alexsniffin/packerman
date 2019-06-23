# packerman

[![Build Status](https://travis-ci.com/alexsniffin/packerman.svg?branch=master)](https://travis-ci.com/alexsniffin/packerman)

This is a lightweight library for basic and general-purpose bin-packing of a collection with arbitrary values.

## Supported Language Version
- Scala 2.12

## Usage

The design of this library allows you to define your usage on how to bin-pack your input collection.

### Example
```scala
case class Input(key: String, value: Int)

val collection = Seq(
    Input("29b482bca2af868b0cc6c7c409fe3d14", 588665550),
    Input("d724b748d259fa0f39a4ac776a86ad61", 1045661516), 
    Input("df1662f0aed98c78c192654461afb6f6", 232359110)
  )

val com.alexsniffin.packerman = PackermanEngine(collection)
  .groupBy(x => x.key)
  .packBy(x => x.value)
  .distributionStrategy(UniformDistribution(true, .5))
  .compute()

val output = com.alexsniffin.packerman match {
  case Right(result) => result
  case l @ Left(err) => l // handle error
}

```

## Distribution Strategies

#### Uniform Distribution

Distributes the field specified by `packBy` uniformly from the specified `groupBy` field.

|Parameters|Description|
|---|---|
|weighted|Specifies if the field will be adjusted against other groups in the collection when distributed.|
|limit|Specifies the upper limit of a group for which to distribute. If the `amount of groups * limit < 1`, the limit is too small.|
