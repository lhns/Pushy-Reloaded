package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Physics}

case class Ball(color: Ball.Color) extends TileInstance {
  override type Self = Ball

  override def factory: TileFactory[Ball] = Ball

  override lazy val image: Image = Ball.images(this)

  override def physics: Physics = Physics.Pushable
}

object Ball extends TileFactory[Ball] {

  sealed trait Color

  object Color {

    case object Red extends Color

    case object Blue extends Color

    case object Green extends Color

  }

  private val images: Map[Ball, Image] = List(
    Ball(Color.Red),
    Ball(Color.Blue),
    Ball(Color.Green)
  ).zipWithIndex.map {
    case (tile, i) => tile -> Image(s"/assets/images/${i + 1}.png")
  }.toMap

  override def defaultInstance: Ball = Ball(Color.Red)

  override val ids: List[Int] = List(1, 2, 3)

  override def fromId(id: Int): Ball = Ball(id match {
    case 1 => Color.Red
    case 2 => Color.Blue
    case 3 => Color.Green
  })
}
