package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

case class BallHole private(color: Ball.Color) extends TileInstance {
  override type Self = BallHole

  override def factory: TileFactory[BallHole] = BallHole

  override lazy val image: Image = Image(s"/assets/images/${color.index + 4}.bmp")

  override def pushable: Pushable = Pushable.Empty

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by.as(Ball) match {
      case Some(ball) =>
        if (ball.color == color)
          Pushable.Empty.withAction {
            world.remove(byPos, ball)
          }
        else
          Pushable.Solid.withoutAction

      case _ =>
        super.pushable(world, pos, direction, by, byPos)
    }
}

object BallHole extends TileFactory[BallHole] {
  def apply(color: Ball.Color): BallHole = cached(new BallHole(color))

  override val variants: Seq[BallHole] = Ball.Color.values.map(new BallHole(_))

  override val ids: List[Int] = List(4, 5, 6)

  override def fromId(id: Int): BallHole = BallHole(id match {
    case 4 => Ball.Color.Red
    case 5 => Ball.Color.Blue
    case 6 => Ball.Color.Green
  })
}
