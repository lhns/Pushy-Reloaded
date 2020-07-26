package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._
import de.lolhens.pushyreloaded.tile.Ball.Color

case class BallTarget private(color: Ball.Color) extends TileInstance {
  override type Self = BallTarget

  override def factory: TileFactory[BallTarget] = BallTarget

  override lazy val id: Int = color match {
    case _ if color.index <= Color.Green.index => color.index + 4
    case Color.Gray => 101
    case Color.Yellow => 114
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

object BallTarget extends TileFactory[BallTarget] {
  def apply(color: Ball.Color): BallTarget = cached(new BallTarget(color))

  override val variants: Seq[BallTarget] = Ball.Color.values.map(new BallTarget(_))
  override val idVariants: Seq[BallTarget] = Ball.Color.idValues.map(new BallTarget(_))
}
