package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.Attributes.Attribute
import de.lolhens.pushyreloaded._

sealed trait Projectile extends SimpleTile[Projectile] {
  override val id: Int = 26

  override def self: Projectile = this

  override val image: Image = defaultImageAsset(extension = "png")

  override val pushable: Pushable = Pushable.Empty

  val ProjectileAttribute: Attribute[Boolean] = Attribute("projectile", () => false)

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(player) if !player.attributes.get(ProjectileAttribute) =>
        Pushable.Empty.withAction {
          world.remove(pos, this)
          player.changeAttributes(_.put(ProjectileAttribute)(true))
        }

      case _ =>
        Pushable.Solid.withoutAction
    }
}

object Projectile extends Projectile
