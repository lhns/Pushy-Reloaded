package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

sealed trait Lock extends SimpleTile[Lock] {
  override val id: Int = 34

  override def self: Lock = this

  override val image: Image = defaultImageAsset()

  override val pushable: Pushable = Pushable.Solid

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) = {
    by match {
      case Player(player) if player.attributes.get(Key.KeyAttribute) > 0 =>
        player.changeAttributes(_.map(Key.KeyAttribute)(_ - 1))
        world.remove(pos, this)

      case _ =>
    }

    super.pushable(world, pos, direction, by, byPos)
  }
}

object Lock extends Lock
