package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._
import de.lolhens.pushyreloaded.tile.ColorChanger.Color

import scala.util.chaining._

case class ColorChanger private(color: Color, green: Boolean) extends TileInstance {
  override type Self = ColorChanger

  override def factory: TileFactory[ColorChanger] = ColorChanger

  def withColor(color: Color): ColorChanger = ColorChanger(color, green)

  def nextColor: Color = color.next(green)

  def withNextColor: ColorChanger = withColor(nextColor)

  override lazy val id: Int = color match {
    case _ if color.index <= Ball.Color.Yellow.index => color.index + 36
    case Color.Green => 112
    case Color.Gray => 119
  }

  override lazy val image: Image = defaultImageAsset()

  override def pushable: Pushable = Pushable.Pushable

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    super.pushable(world, pos, direction, by, byPos).pipe {
      case (Pushable.Pushable, action) =>
        Pushable.Pushable.withAction {
          world.change(pos, this, this.withNextColor)
          action()
        }

      case e => e
    }
}

object ColorChanger extends TileFactory[ColorChanger] {
  def apply(color: Color, green: Boolean = false): ColorChanger = cached(new ColorChanger(color, green))

  override val variants: Seq[ColorChanger] =
    Color.values.flatMap(e => List(new ColorChanger(e, green = false), new ColorChanger(e, green = true)))

  override val idVariants: Seq[ColorChanger] = Color.idValues.map(new ColorChanger(_, green = false))

  sealed abstract class Color(val index: Int) {
    def next(green: Boolean): Color
  }

  object Color {

    case object Red extends Color(0) {
      override def next(green: Boolean): Color = Blue
    }

    case object Blue extends Color(1) {
      override def next(green: Boolean): Color = Yellow
    }

    case object Yellow extends Color(2) {
      override def next(green: Boolean): Color =
        if (green) Green
        else Red
    }

    case object Green extends Color(3) {
      override def next(green: Boolean): Color = Red
    }

    case object Gray extends Color(4) {
      override def next(green: Boolean): Color = Red
    }

    val values: List[Color] = List(Red, Blue, Yellow, Green, Gray)
    val idValues: List[Color] = List(Red, Blue, Yellow)
  }

}
