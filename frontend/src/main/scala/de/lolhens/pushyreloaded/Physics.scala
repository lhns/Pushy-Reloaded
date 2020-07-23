package de.lolhens.pushyreloaded

sealed trait Physics

object Physics {

  case object Solid extends Physics

  case object Empty extends Physics

  case object Pushable extends Physics

}