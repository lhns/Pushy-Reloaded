package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.Image

sealed trait Background extends SimpleTile[Background] with VisualInstance {
  override def self: Background = this

  override val image: Image = defaultImageAsset()

  override val zIndex: Int = Int.MinValue
}

object Background extends Background
