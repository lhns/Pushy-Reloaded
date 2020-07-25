package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.Attributes.Attribute
import de.lolhens.pushyreloaded._

sealed trait ReverseMove extends SimpleTile[ReverseMove] {
  override val id: Int = 25

  override def self: ReverseMove = this

  override val image: Image = Image("/assets/images/25.bmp")

  override val pushable: Pushable = Pushable.Empty

  val ReverseMoveAttribute: Attribute[Boolean] = Attribute("reverse_move", () => false)

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(player) =>
        Pushable.Empty.withAction {
          world.remove(pos, this)
          player.changeAttributes(_.put(ReverseMoveAttribute)(true))
        }

      case _ =>
        Pushable.Solid.withoutAction
    }
}

object ReverseMove extends ReverseMove
