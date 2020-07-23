package de.lolhens.pushyreloaded.gameobj

import de.lolhens.pushyreloaded.{Image, Physics, Vec2i}
import org.scalajs.dom

trait ObjType {
  type Props
  type Inst <: ObjInst {type Type = ObjType.this.type}

  def apply(pos: Vec2i, props: Props): Inst

  final def apply(pos: Vec2i)(implicit ev: Unit =:= Props): Inst = apply(pos, ev(()))

  def unapply(inst: ObjInst): Option[Inst] =
    if (inst.objType == ObjType.this) Some(inst.asInstanceOf[Inst])
    else None

  val id: Int

  val image: Image

  def render(ctx: dom.CanvasRenderingContext2D,
             pos: Vec2i,
             props: Props): Unit = {
    val image = this.image
    if (image.isReady) {
      ctx.drawImage(image.element, pos.x, pos.y, image.element.width, image.element.height)
    }
  }

  val physics: Physics

  val zIndex: Int = 0
}

object ObjType {
  val size: Vec2i = Vec2i(32, 32)

  /*trait Inst {
    val gameObj: ObjType
    val props: gameObj.Props

    def withProps(props: gameObj.Props): Inst = Inst.Impl[gameObj.Props](
      gameObj.asInstanceOf[ObjType {type Props = gameObj.Props}],
      props
    )

    def map(f: gameObj.Props => gameObj.Props): Inst = withProps(f(props))
  }

  object Inst {

    private[ObjType] case class Impl[P](gameObj: ObjType {type Props = P}, props: P) extends Inst

  }*/

}
