package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Direction, Vec2i, World}
import org.scalajs.dom.CanvasRenderingContext2D

trait Directional extends TileInstance with RenderRotated {
  def direction: Direction

  override def render(world: World, pos: Vec2i, ctx: CanvasRenderingContext2D, renderPos: Vec2i): Unit =
    renderRotated(world, pos, ctx, renderPos, direction.degrees * Math.PI / 180)
}
