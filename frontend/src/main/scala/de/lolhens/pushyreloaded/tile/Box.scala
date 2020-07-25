package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Pushable}

sealed trait Box extends SimpleTile[Box] {
  override val id: Int = 9

  override def self: Box = this

  override val image: Image = Image("/assets/images/9.bmp")

  override val pushable: Pushable = Pushable.Pushable
}

object Box extends Box
