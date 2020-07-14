package de.lolhens.pushyreloaded.gameobj

import de.lolhens.pushyreloaded.gameobj.GameObj.{Inst, Physics}
import de.lolhens.pushyreloaded.{Image, Vec2}
import org.scalajs.dom

trait GameObj {
  type Props

  def apply(props: Props): GameObj.Inst = GameObj.Inst.Impl[Props](this, props)

  def apply()(implicit ev: Unit =:= Props): GameObj.Inst = apply(ev(()))

  def unapply(inst: Inst): Option[Props] =
    if (inst.gameObj == GameObj.this) Some(inst.props.asInstanceOf[Props])
    else None

  val id: Int

  val image: Image

  def render(ctx: dom.CanvasRenderingContext2D,
             pos: Vec2,
             props: Props): Unit = {
    val image = this.image
    if (image.isReady) {
      ctx.drawImage(image.element, pos.x, pos.y, image.element.width, image.element.height)
    }
  }

  val physics: Physics

  val zIndex: Int = 0
}

object GameObj {
  val size: Vec2 = Vec2(32, 32)

  sealed trait Physics

  object Physics {

    case object Solid extends Physics

    case object Empty extends Physics

    case object Pushable extends Physics

  }

  trait Inst {
    val gameObj: GameObj
    val props: gameObj.Props

    def withProps(props: gameObj.Props): Inst = Inst.Impl[gameObj.Props](
      gameObj.asInstanceOf[GameObj {type Props = gameObj.Props}],
      props
    )

    def map(f: gameObj.Props => gameObj.Props): Inst = withProps(f(props))
  }

  object Inst {

    private[GameObj] case class Impl[P](gameObj: GameObj {type Props = P}, props: P) extends Inst

  }

}
