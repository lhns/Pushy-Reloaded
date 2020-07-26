package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.Attributes.Attribute
import de.lolhens.pushyreloaded._

sealed trait FarMove extends SimpleTile[FarMove] {
  override val id: Int = 35

  override def self: FarMove = this

  override val image: Image = defaultImageAsset()

  override val pushable: Pushable = Pushable.Empty

  val FarMoveAttribute: Attribute[Boolean] = Attribute("far_move", () => false)

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(player) =>
        Pushable.Empty.withAction {
          world.remove(pos, this)
          player.changeAttributes(_.put(FarMoveAttribute)(true))
        }

      case _ =>
        Pushable.Solid.withoutAction
    }
}

object FarMove extends FarMove
