package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Direction, Vec2i}
import org.scalajs.dom.CanvasRenderingContext2D

trait Directional extends TileInstance {
  def direction: Direction

  override def render(ctx: CanvasRenderingContext2D, pos: Vec2i): Unit = {
    ctx.save()
    ctx.setTransform(1, 0, 0, 1, pos.x + TileInstance.size.x / 2, pos.y + TileInstance.size.y / 2)
    ctx.rotate(direction.degrees * Math.PI / 180)
    super.render(ctx, Vec2i(-TileInstance.size.x / 2, -TileInstance.size.y / 2))
    ctx.restore()
  }
}
