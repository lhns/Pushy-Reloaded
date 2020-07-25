package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

case class BallHole private(color: Ball.Color) extends TileInstance {
  override type Self = BallHole

  override def factory: TileFactory[BallHole] = BallHole

  override lazy val id: Int = color match {
    case _ if color.index <= Ball.Color.Green.index => color.index + 4
    case Ball.Color.Gray => 101
    case Ball.Color.Yellow => 114
  }

  override lazy val image: Image = defaultImageAsset()

  override val pushable: Pushable = Pushable.Solid

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(_) =>
        Pushable.Empty.withoutAction

      case Ball(ball) if ball.color == color =>
        Pushable.Empty.withAction {
          world.remove(byPos, ball)
        }

      case _ =>
        super.pushable(world, pos, direction, by, byPos)
    }
}

object BallHole extends TileFactory[BallHole] {
  def apply(color: Ball.Color): BallHole = cached(new BallHole(color))

  override val variants: Seq[BallHole] = Ball.Color.values.map(new BallHole(_))
}
