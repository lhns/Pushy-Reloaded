package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.tile.Ball.Color
import de.lolhens.pushyreloaded.{Image, Physics}

case class Ball(color: Ball.Color) extends TileInstance {
  override type Self = Ball

  override def factory: TileFactory[Ball] = Ball

  private def colorId: Int = color match {
    case Color.Red => 1
    case Color.Blue => 2
    case Color.Green => 3
  }

  override val image: Image = Image(s"/assets/images/$colorId.png")

  override def physics: Physics = Physics.Pushable
}

object Ball extends TileFactory[Ball] {

  sealed trait Color

  object Color {

    case object Red extends Color

    case object Blue extends Color

    case object Green extends Color

  }

  override def defaultInstance: Ball = Ball(Color.Red)

  override val ids: List[Int] = List(1, 2, 3)

  override def fromId(id: Int): Ball = Ball(id match {
    case 1 => Color.Red
    case 2 => Color.Blue
    case 3 => Color.Green
  })
}
