package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

sealed trait SecretDoor extends SimpleTile[SecretDoor] {
  override val id: Int = 14

  override def self: SecretDoor = this

  override val image: Image = defaultImageAsset()
  val imageOpen: Image = Resource.image("103.bmp")

  private def isOpen(world: World, pos: Vec2i): Boolean =
    (world.get(pos) ++ world.list(Button).flatMap(e => world.get(e._1))).exists { tile =>
      tile.pushable == Pushable.Pushable
    }

  override def image(world: World, pos: Vec2i): Image =
    if (isOpen(world, pos)) imageOpen
    else image

  override val pushable: Pushable = Pushable.Empty

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    if (isOpen(world, pos)) super.pushable(world, pos, direction, by, byPos)
    else Pushable.Solid.withoutAction
}

object SecretDoor extends SecretDoor
