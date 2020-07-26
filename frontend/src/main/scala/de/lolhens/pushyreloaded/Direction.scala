package de.lolhens.pushyreloaded

sealed trait Direction {
  def opposite: Direction

  def horizontal: Boolean

  def degrees: Int
}

object Direction {

  case object Left extends Direction {
    override def opposite: Direction = Right

    override def horizontal: Boolean = true

    override def degrees: Int = 270
  }

  case object Up extends Direction {
    override def opposite: Direction = Down

    override def horizontal: Boolean = false

    override def degrees: Int = 0
  }

  case object Right extends Direction {
    override def opposite: Direction = Left

    override def horizontal: Boolean = true

    override def degrees: Int = 90
  }

  case object Down extends Direction {
    override def opposite: Direction = Up

    override def horizontal: Boolean = false

    override def degrees: Int = 180
  }

  val values: List[Direction] = List(Left, Up, Right, Down)
}
