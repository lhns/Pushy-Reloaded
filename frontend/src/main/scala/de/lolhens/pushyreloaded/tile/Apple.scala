package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

sealed trait Apple extends SimpleTile[Apple] {
  override val id: Int = 21

  override def self: Apple = this

  override val image: Image = Image("/assets/images/21.bmp")

  override val pushable: Pushable = Pushable.Solid

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(_) =>
        Pushable.Empty.withAction {
          world.remove(pos, this)
        }

      case _ =>
        super.pushable(world, pos, direction, by, byPos)
    }

  override def missionComplete(world: World, pos: Vec2i): Boolean = false
}

object Apple extends Apple
