package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

sealed trait BoxTarget extends SimpleTile[BoxTarget] {
  override val id: Int = 18

  override def self: BoxTarget = this

  override val image: Image = defaultImageAsset()

  override val pushable: Pushable = Pushable.Empty

  override def missionComplete(world: World, pos: Vec2i): Boolean =
    world.get(pos, Box).nonEmpty
}

object BoxTarget extends BoxTarget
