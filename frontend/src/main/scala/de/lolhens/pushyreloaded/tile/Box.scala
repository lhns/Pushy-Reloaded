package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Pushable, Vec2i, World}

sealed trait Box extends SimpleTile[Box] {
  override val image: Image = Image("/assets/images/9.bmp")

  override val pushable: Pushable = Pushable.Pushable

  override val ids: List[Int] = List(9)

  override def fromId(id: Int): Box = this

  override def variants: Seq[Box] = Seq(this)

  override def missionComplete(world: World, pos: Vec2i): Boolean =
    world.get(pos, BoxTarget).nonEmpty
}

object Box extends Box
