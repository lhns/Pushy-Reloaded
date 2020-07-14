package de.lolhens.pushyreloaded

case class Vec2(x: Int, y: Int) {
  def withX(x: Int): Vec2 = copy(x = x)

  def withY(y: Int): Vec2 = copy(y = y)

  def map(x: Int => Int, y: Int => Int): Vec2 =
    Vec2(x(this.x), y(this.y))

  def offset(direction: Direction): Vec2 = direction match {
    case Direction.Left => withX(x - 1)
    case Direction.Up => withY(y - 1)
    case Direction.Right => withX(x + 1)
    case Direction.Down => withY(y + 1)
  }
}

object Vec2 {
  val zero: Vec2 = Vec2(0, 0)
}
