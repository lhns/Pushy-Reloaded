package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.Attributes.Attribute
import de.lolhens.pushyreloaded._

sealed trait Transformer extends SimpleTile[Transformer] {
  override val id: Int = 27

  override def self: Transformer = this

  override val image: Image = defaultImageAsset()

  override val pushable: Pushable = Pushable.Empty

  val ChargedAttribute: Attribute[Boolean] = Attribute("charged", () => false)

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(player) =>
        Pushable.Empty.withAction {
          if (player.attributes.get(ChargedAttribute)) {
            Main.restart()
          } else {
            player.changeAttributes(_.put(ChargedAttribute)(true))
          }
        }

      case _ =>
        Pushable.Solid.withoutAction
    }
}

object Transformer extends Transformer
