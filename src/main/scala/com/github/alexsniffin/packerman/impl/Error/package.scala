package com.github.alexsniffin.packerman.impl

package object Error {

  sealed trait Error {
    val reason: String
  }

  case class ThrowableError(reason: String) extends Error

  case class AlgorithmError(reason: String) extends Error

  case class InvalidAlgorithmError(reason: String) extends Error

  case class InvalidAlgorithmParameterError(reason: String) extends Error

  case class UnknownError(reason: String) extends Error

  case class MissingParametersError(reason: String) extends Error

}
