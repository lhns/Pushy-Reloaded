package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.Attributes.Attribute
import de.lolhens.pushyreloaded._

sealed trait Stamp extends SimpleTile[Stamp] {
  override val id: Int = 11

  override def self: Stamp = this

  override val image: Image = defaultImageAsset()

  override val pushable: Pushable = Pushable.Empty

  val StampAttribute: Attribute[Boolean] = Attribute("stamp", () => false)

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(player) =>
        Pushable.Empty.withAction {
          player.changeAttributes(_.put(StampAttribute)(true))
        }

      case _ =>
        Pushable.Solid.withoutAction
    }
}

object Stamp extends Stamp
