package de.lolhens.pushyreloaded

sealed trait Direction {
  def opposite: Direction

  val degrees: Int
}

object Direction {

  case object Left extends Direction {
    override def opposite: Direction = Right

    override val degrees: Int = 270
  }

  case object Up extends Direction {
    override def opposite: Direction = Down

    override val degrees: Int = 0
  }

  case object Right extends Direction {
    override def opposite: Direction = Left

    override val degrees: Int = 90
  }

  case object Down extends Direction {
    override def opposite: Direction = Up

    override val degrees: Int = 180
  }

  val values: List[Direction] = List(Left, Up, Right, Down)
}
