package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.Attributes.Attribute
import de.lolhens.pushyreloaded._

sealed trait Key extends SimpleTile[Key] {
  override val id: Int = 33

  override def self: Key = this

  override val image: Image = defaultImageAsset()

  override val pushable: Pushable = Pushable.Empty

  val KeyAttribute: Attribute[Int] = Attribute("key", () => 0)

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(player) =>
        Pushable.Empty.withAction {
          world.remove(pos, this)
          player.changeAttributes(_.map(KeyAttribute)(_ + 1))
        }

      case _ =>
        Pushable.Solid.withoutAction
    }
}

object Key extends Key
