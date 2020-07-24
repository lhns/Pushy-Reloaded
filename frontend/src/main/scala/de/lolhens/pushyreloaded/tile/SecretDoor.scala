package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

sealed trait SecretDoor extends SimpleTile[SecretDoor] {
  override val image: Image = Image("/assets/images/14.bmp")
  val imageOpen: Image = Image("/assets/images/103.bmp")

  private def buttonPressed(world: World): Boolean =
    world.list(Button).exists(e => world.get(e._1).exists { tile =>
      tile.is(Player) || tile.pushable == Pushable.Pushable
    })

  override def image(world: World, pos: Vec2i): Image =
    if (buttonPressed(world)) imageOpen
    else image

  override val ids: List[Int] = List(12)

  override def fromId(id: Int): SecretDoor = this

  override def variants: Seq[SecretDoor] = Seq(this)

  override val pushable: Pushable = Pushable.Empty

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    if (buttonPressed(world)) super.pushable(world, pos, direction, by, byPos)
    else Pushable.Solid.withoutAction
}

object SecretDoor extends SecretDoor
