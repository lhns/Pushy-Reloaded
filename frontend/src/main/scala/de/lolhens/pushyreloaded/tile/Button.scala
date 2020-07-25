package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

sealed trait Button extends SimpleTile[Button] {
  override val id: Int = 13

  override def self: Button = this

  override val image: Image = Image("/assets/images/13.bmp")

  override val pushable: Pushable = Pushable.Empty

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(_) =>
        Pushable.Empty.withoutAction

      case pushableTile if pushableTile.pushable == Pushable.Pushable =>
        Pushable.Empty.withoutAction

      case _ =>
        Pushable.Solid.withoutAction
    }
}

object Button extends Button
