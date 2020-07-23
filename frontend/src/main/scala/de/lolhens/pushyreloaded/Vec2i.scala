package de.lolhens.pushyreloaded

case class Vec2i(x: Int, y: Int) {
  def withX(x: Int): Vec2i = copy(x = x)

  def withY(y: Int): Vec2i = copy(y = y)

  def map(x: Int => Int, y: Int => Int): Vec2i =
    Vec2i(x(this.x), y(this.y))

  def offset(direction: Direction): Vec2i = direction match {
    case Direction.Left => withX(x - 1)
    case Direction.Up => withY(y - 1)
    case Direction.Right => withX(x + 1)
    case Direction.Down => withY(y + 1)
  }
}

object Vec2i {
  val zero: Vec2i = Vec2i(0, 0)
}
