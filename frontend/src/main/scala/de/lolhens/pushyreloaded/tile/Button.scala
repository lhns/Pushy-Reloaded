package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

sealed trait Button extends SimpleTile[Button] {
  override val image: Image = Image("/assets/images/13.bmp")

  override val ids: List[Int] = List(13)

  override def fromId(id: Int): Button = this

  override def variants: Seq[Button] = Seq(this)

  override val pushable: Pushable = Pushable.Empty

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(_) | Box(_) | Ball(_) =>
        super.pushable(world, pos, direction, by, byPos)

      case _ =>
        Pushable.Solid.withoutAction
    }
}

object Button extends Button
