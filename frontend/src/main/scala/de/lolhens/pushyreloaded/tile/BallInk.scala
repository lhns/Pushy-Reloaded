package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

case class BallInk private(color: Ball.Color) extends TileInstance {
  override type Self = BallInk

  override def factory: TileFactory[BallInk] = BallInk

  override lazy val id: Int = color match {
    case _ if color.index <= Ball.Color.Green.index => color.index + 15
    case Ball.Color.Gray => 102
    case Ball.Color.Yellow => 115
  }

  override lazy val image: Image = defaultImageAsset()

  override def pushable: Pushable = Pushable.Solid

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(_) =>
        Pushable.Empty.withoutAction

      case Ball(ball) =>
        Pushable.Empty.withAction {
          world.remove(pos, this)
          world.change(byPos, ball, ball.withColor(color))
        }

      case _ =>
        super.pushable(world, pos, direction, by, byPos)
    }
}

object BallInk extends TileFactory[BallInk] {
  def apply(color: Ball.Color): BallInk = cached(new BallInk(color))

  override val variants: Seq[BallInk] = Ball.Color.values.map(new BallInk(_))
  override val idVariants: Seq[BallInk] = Ball.Color.idValues.map(new BallInk(_))
}
