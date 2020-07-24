package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

case class BallInk private(color: Ball.Color) extends TileInstance {
  override type Self = BallInk

  override def factory: TileFactory[BallInk] = BallInk

  override lazy val image: Image = Image(s"/assets/images/${color.index + 15}.bmp")

  override def pushable: Pushable = Pushable.Empty

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Ball(ball) =>
        Pushable.Empty.withAction {
          world.remove(pos, this)
          world.change(byPos, ball, ball.withColor(color))
        }

      case _ =>
        Pushable.Solid.withoutAction
    }
}

object BallInk extends TileFactory[BallInk] {
  def apply(color: Ball.Color): BallInk = cached(new BallInk(color))

  override val variants: Seq[BallInk] = Ball.Color.values.map(new BallInk(_))

  override val ids: List[Int] = List(15, 16, 17)

  override def fromId(id: Int): BallInk = BallInk(id match {
    case 15 => Ball.Color.Red
    case 16 => Ball.Color.Blue
    case 17 => Ball.Color.Green
  })
}
