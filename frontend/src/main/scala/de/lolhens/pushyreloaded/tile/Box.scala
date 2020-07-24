package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Pushable, Vec2i, World}

sealed trait Box extends SimpleTile[Box] {
  override val id: Int = 9

  override def self: Box = this

  override val image: Image = Image("/assets/images/9.bmp")

  override val pushable: Pushable = Pushable.Pushable

  override def missionComplete(world: World, pos: Vec2i): Boolean =
    world.get(pos, BoxTarget).nonEmpty
}

object Box extends Box
