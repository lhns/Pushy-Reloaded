package de.lolhens.pushyreloaded

case class Vec2i(x: Int, y: Int) {
  def withX(x: Int): Vec2i = copy(x = x)

  def withY(y: Int): Vec2i = copy(y = y)

  def map(x: Int => Int, y: Int => Int): Vec2i = Vec2i(x(this.x), y(this.y))

  def offset(offset: Vec2i): Vec2i = Vec2i(x + offset.x, y + offset.y)

  def offset(direction: Direction): Vec2i = direction match {
    case Direction.Left => offset(Vec2i.Left)
    case Direction.Up => offset(Vec2i.Up)
    case Direction.Right => offset(Vec2i.Right)
    case Direction.Down => offset(Vec2i.Down)
  }
}

object Vec2i {
  val Zero: Vec2i = Vec2i(0, 0)

  val Left: Vec2i = Vec2i(-1, 0)
  val Up: Vec2i = Vec2i(0, -1)
  val Right: Vec2i = Vec2i(1, 0)
  val Down: Vec2i = Vec2i(0, 1)
}
