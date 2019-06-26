name := "packerman"
description := "A lightweight library for general purpose bin-packing of a collection with arbitrary values."
version := "0.4.0-RC1"
organization := "com.github.alexsniffin"
scalaVersion := "2.12.8"
credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credential")
publishTo := sonatypePublishTo.value

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test