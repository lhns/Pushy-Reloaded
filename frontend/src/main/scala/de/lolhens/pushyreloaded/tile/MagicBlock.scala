package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

import scala.util.chaining._

sealed trait MagicBlock extends SimpleTile[MagicBlock] {
  override val id: Int = 19

  override def self: MagicBlock = this

  override val image: Image = Image("/assets/images/19.bmp")

  override val pushable: Pushable = Pushable.Pushable

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    super.pushable(world, pos, direction, by, byPos).pipe {
      case (pushable, action) => pushable.maybeWithAction {
        action()
        val newPos = pos.offset(direction)
        val otherBlocks = Direction.values.map(newPos.offset).flatMap(pos => world.get(pos, MagicBlock).map((pos, _)))
        if (otherBlocks.nonEmpty) {
          world.remove(newPos, this)
          otherBlocks.forall(e => world.remove(e._1, e._2))
        }
      }
    }

  override def missionComplete(world: World, pos: Vec2i): Boolean = false

  override def removedFromWorld(world: World, pos: Vec2i, moved: Boolean): Unit =
    if (!moved)
      world.add(pos, MagicBlockVanishAnimation(0.4))
}

object MagicBlock extends MagicBlock
