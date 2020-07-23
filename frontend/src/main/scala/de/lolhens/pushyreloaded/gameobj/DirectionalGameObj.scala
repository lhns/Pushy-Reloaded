package de.lolhens.pushyreloaded.gameobj

import de.lolhens.pushyreloaded.gameobj.DirectionalGameObj.DirectionProps
import de.lolhens.pushyreloaded.{Direction, Vec2i}
import org.scalajs.dom.CanvasRenderingContext2D

trait DirectionalGameObj extends ObjType {
  override type Props <: DirectionProps

  override def render(ctx: CanvasRenderingContext2D, pos: Vec2i, props: Props): Unit = {
    ctx.save()
    ctx.setTransform(1, 0, 0, 1, pos.x + ObjType.size.x / 2, pos.y + ObjType.size.y / 2)
    ctx.rotate(props.direction.degrees * Math.PI / 180)
    super.render(ctx, Vec2i(-ObjType.size.x / 2, -ObjType.size.y / 2), props)
    ctx.restore()
  }
}

object DirectionalGameObj {

  trait DirectionProps {
    def direction: Direction
  }

}
