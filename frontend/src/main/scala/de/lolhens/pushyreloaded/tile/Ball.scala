package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Pushable, Vec2i, World}

case class Ball private(color: Ball.Color) extends TileInstance {
  override type Self = Ball

  override def factory: TileFactory[Ball] = Ball

  override lazy val image: Image = Image(s"/assets/images/${color.index + 1}.png")

  override def pushable: Pushable = Pushable.Pushable

  override def missionComplete(world: World, pos: Vec2i): Boolean = false
}

object Ball extends TileFactory[Ball] {
  def apply(color: Ball.Color): Ball = cached(new Ball(color))

  override val variants: Seq[Ball] = Color.values.map(new Ball(_))

  sealed abstract class Color(val index: Int)

  object Color {

    case object Red extends Color(0)

    case object Blue extends Color(1)

    case object Green extends Color(2)

    val values: List[Color] = List(Red, Blue, Green)
  }

  override val ids: List[Int] = List(1, 2, 3)

  override def fromId(id: Int): Ball = Ball(id match {
    case 1 => Color.Red
    case 2 => Color.Blue
    case 3 => Color.Green
  })
}
