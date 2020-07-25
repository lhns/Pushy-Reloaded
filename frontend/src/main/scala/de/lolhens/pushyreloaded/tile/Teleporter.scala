package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

import scala.util.Random

sealed trait Teleporter extends SimpleTile[Teleporter] {
  override val id: Int = 8

  override def self: Teleporter = this

  override val image: Image = defaultImageAsset()

  override val pushable: Pushable = Pushable.Empty

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(player) =>
        val otherTeleporters = world.list(Teleporter).filterNot(_._1 == pos)
        val otherTeleporter = Some(otherTeleporters.size).filter(_ > 0).map(size => otherTeleporters(Random.nextInt(size)))
        otherTeleporter.foreach(e => world.moveTo(byPos, player, e._1))

        super.pushable(world, pos, direction, by, byPos)

      case _ =>
        Pushable.Solid.withoutAction
    }
}

object Teleporter extends Teleporter
