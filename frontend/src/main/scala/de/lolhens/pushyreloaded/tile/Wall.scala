package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Pushable}

sealed trait Wall extends SimpleTile[Wall] {
  override val id: Int = 12

  override def self: Wall = this

  override val image: Image = defaultImageAsset()

  override val pushable: Pushable = Pushable.Solid
}

object Wall extends Wall
