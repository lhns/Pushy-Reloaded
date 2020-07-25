package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

sealed trait House extends SimpleTile[House] {
  override val id: Int = 7

  override def self: House = this

  override val image: Image = Image("/assets/images/7.bmp")

  override val pushable: Pushable = Pushable.Solid

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(_) =>
        if (world.list.forall(e => e._2.missionComplete(world, e._1))) {
          Pushable.Empty.withAction {
            Main.next()
          }
        } else
          Pushable.Empty.withoutAction

      case _ =>
        super.pushable(world, pos, direction, by, byPos)
    }
}

object House extends House
