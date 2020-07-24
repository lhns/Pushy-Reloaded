package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

sealed trait Stamp extends SimpleTile[Stamp] {
  override val id: Int = 11

  override def self: Stamp = this

  override val image: Image = Image("/assets/images/11.bmp")

  override val pushable: Pushable = Pushable.Empty

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(_) =>
        super.pushable(world, pos, direction, by, byPos)

      case _ =>
        Pushable.Solid.withoutAction
    }
}

object Stamp extends Stamp
