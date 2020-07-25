package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Vec2i, World}
import org.scalajs.dom.CanvasRenderingContext2D

class MotionSensorAnimation private() extends VisualInstance with RenderRotated {
  override type Self = MotionSensorAnimation

  override def factory: TileFactory[MotionSensorAnimation] = MotionSensorAnimation

  override def image: Image = MotionSensorAnimation.image

  private var time: Double = 0

  override def render(world: World, pos: Vec2i, ctx: CanvasRenderingContext2D, d: Double, renderPos: Vec2i): Unit = {
    time += d
    renderRotated(world, pos, ctx, d, renderPos, (time * 3) % (Math.PI * 2))
  }
}

object MotionSensorAnimation extends VisualFactory[MotionSensorAnimation] {
  def apply(): MotionSensorAnimation = new MotionSensorAnimation()

  private val image: Image = Image("/assets/images/108.png")
}
