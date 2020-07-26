package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Resource, Vec2i, World}
import org.scalajs.dom.CanvasRenderingContext2D

class MotionSensorAnimation private() extends VisualInstance with RenderRotated {
  override type Self = MotionSensorAnimation

  override def factory: TileFactory[MotionSensorAnimation] = MotionSensorAnimation

  override def image: Image = MotionSensorAnimation.image

  override def render(world: World, pos: Vec2i, ctx: CanvasRenderingContext2D, renderPos: Vec2i): Unit =
    renderRotated(world, pos, ctx, renderPos, (world.time * 3) % (Math.PI * 2))
}

object MotionSensorAnimation extends VisualFactory[MotionSensorAnimation] {
  def apply(): MotionSensorAnimation = new MotionSensorAnimation()

  private val image: Image = Resource.image("108.png")
}
