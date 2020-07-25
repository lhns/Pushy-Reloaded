package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Vec2i, World}
import org.scalajs.dom.CanvasRenderingContext2D

trait RenderRotated extends TileInstance {
  protected def renderRotated(world: World,
                              pos: Vec2i,
                              ctx: CanvasRenderingContext2D,
                              d: Double,
                              renderPos: Vec2i,
                              rad: Double): Unit = {
    ctx.save()
    ctx.setTransform(1, 0, 0, 1, renderPos.x + TileInstance.size.x / 2, renderPos.y + TileInstance.size.y / 2)
    ctx.rotate(rad)
    super.render(world, pos, ctx, d, Vec2i(-TileInstance.size.x / 2, -TileInstance.size.y / 2))
    ctx.restore()
  }
}
