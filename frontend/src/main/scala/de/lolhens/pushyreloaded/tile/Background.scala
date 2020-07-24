package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Pushable}

sealed trait Background extends SimpleTile[Background] {
  override val id: Int = 0

  override def self: Background = this

  override val image: Image = Image("/assets/images/0.bmp")

  override val zIndex: Int = Int.MinValue

  override val pushable: Pushable = Pushable.Empty
}

object Background extends Background
