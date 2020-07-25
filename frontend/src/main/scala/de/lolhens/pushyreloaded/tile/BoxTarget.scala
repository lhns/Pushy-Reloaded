package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

sealed trait BoxTarget extends SimpleTile[BoxTarget] {
  override val id: Int = 18

  override def self: BoxTarget = this

  override val image: Image = Image("/assets/images/18.bmp")

  override val pushable: Pushable = Pushable.Empty

  override def missionComplete(world: World, pos: Vec2i): Boolean =
    world.get(pos, Box).nonEmpty

  /*override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(_) | Box(_) =>
        super.pushable(world, pos, direction, by, byPos)

      case _ =>
        Pushable.Solid.withoutAction
    }*/
}

object BoxTarget extends BoxTarget
