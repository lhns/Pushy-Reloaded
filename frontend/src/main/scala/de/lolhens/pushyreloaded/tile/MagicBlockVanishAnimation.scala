package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Vec2i, World}
import org.scalajs.dom.CanvasRenderingContext2D

class MagicBlockVanishAnimation private(delay: Double) extends VisualInstance {
  override type Self = MagicBlockVanishAnimation

  override def factory: TileFactory[MagicBlockVanishAnimation] = MagicBlockVanishAnimation

  override def image: Image = MagicBlock.image

  private var time: Double = 0

  override def render(world: World, pos: Vec2i, ctx: CanvasRenderingContext2D, d: Double, renderPos: Vec2i): Unit = {
    time += d
    if (time > delay)
      world.remove(pos, this)

    super.render(world, pos, ctx, d, renderPos)
  }
}

object MagicBlockVanishAnimation extends VisualFactory[MagicBlockVanishAnimation] {
  def apply(delay: Double): MagicBlockVanishAnimation = new MagicBlockVanishAnimation(delay)
}
