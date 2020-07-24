package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Direction, Vec2i, World}
import org.scalajs.dom.CanvasRenderingContext2D

trait Directional extends TileInstance {
  def direction: Direction

  override def render(ctx: CanvasRenderingContext2D, world: World, pos: Vec2i): Unit = {
    ctx.save()
    ctx.setTransform(1, 0, 0, 1, pos.x + TileInstance.size.x / 2, pos.y + TileInstance.size.y / 2)
    ctx.rotate(direction.degrees * Math.PI / 180)
    super.render(ctx, world, Vec2i(-TileInstance.size.x / 2, -TileInstance.size.y / 2))
    ctx.restore()
  }
}
