package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Direction, Image, Vec2i, World}
import org.scalajs.dom.CanvasRenderingContext2D

class ProjectileThrowAnimation private(direction: Direction) extends VisualInstance {
  override type Self = ProjectileThrowAnimation

  override def factory: TileFactory[ProjectileThrowAnimation] = ProjectileThrowAnimation

  override def image: Image = ProjectileCarryAnimation.image

  override val zIndex: Int = Player.zIndex + 1

  private var lastMoved: Double = 0

  override def addedToWorld(world: World, pos: Vec2i, moved: Boolean): Unit = {
    lastMoved = world.time
    world.get(pos, ProjectileTarget).foreach(world.remove(pos, _))
  }

  override def update(world: World, pos: Vec2i): Unit =
    if (world.time - lastMoved > ProjectileThrowAnimation.moveInterval) {
      val offset = pos.offset(direction)
      if (world.inside(offset))
        world.moveTo(pos, this, offset)
      else
        world.remove(pos, this)
    }

  override def render(world: World, pos: Vec2i, ctx: CanvasRenderingContext2D, renderPos: Vec2i): Unit = {
    val offset = (world.time - lastMoved) / ProjectileThrowAnimation.moveInterval

    val offsetRenderPos =
      direction match {
        case Direction.Left => renderPos.mapX(_ - (offset * TileInstance.size.x).toInt)
        case Direction.Up => renderPos.mapY(_ - (offset * TileInstance.size.y).toInt)
        case Direction.Right => renderPos.mapX(_ + (offset * TileInstance.size.x).toInt)
        case Direction.Down => renderPos.mapY(_ + (offset * TileInstance.size.y).toInt)
      }

    super.render(world, pos, ctx, offsetRenderPos)
  }
}

object ProjectileThrowAnimation extends VisualFactory[ProjectileThrowAnimation] {
  def apply(direction: Direction): ProjectileThrowAnimation = new ProjectileThrowAnimation(direction)

  val moveInterval: Double = 0.02
}
