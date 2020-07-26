package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.tile.Ball.Color
import de.lolhens.pushyreloaded.{Image, Pushable, Vec2i, World}

case class Ball private(color: Color) extends TileInstance {
  override type Self = Ball

  override def factory: TileFactory[Ball] = Ball

  def withColor(color: Color): Ball = Ball(color)

  override lazy val id: Int = color match {
    case _ if color.index <= Color.Green.index => color.index + 1
    case Color.Gray => 100
    case Color.Yellow => 113
  }

  override lazy val image: Image = defaultImageAsset(extension = "png")

  override def pushable: Pushable = Pushable.Pushable

  override def missionComplete(world: World, pos: Vec2i): Boolean = false
}

object Ball extends TileFactory[Ball] {
  def apply(color: Color): Ball = cached(new Ball(color))

  override val variants: Seq[Ball] = Color.values.map(new Ball(_))
  override val idVariants: Seq[Ball] = Color.idValues.map(new Ball(_))

  sealed abstract class Color(val index: Int)

  object Color {

    case object Red extends Color(0)

    case object Blue extends Color(1)

    case object Green extends Color(2)

    case object Yellow extends Color(3)

    case object Gray extends Color(4)

    val values: List[Color] = List(Red, Blue, Green, Yellow, Gray)
    val idValues: List[Color] = List(Red, Blue, Green)
  }
}
