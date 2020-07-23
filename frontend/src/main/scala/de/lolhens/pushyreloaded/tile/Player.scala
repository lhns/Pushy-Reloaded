package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Direction, Image, Physics}

case class Player(var direction: Direction) extends TileInstance with Directional {
  override type Self = Player

  override def factory: TileFactory[Player] = Player

  override val image: Image = Image("/assets/images/10.png")
  override val physics: Physics = Physics.Solid
}

object Player extends TileFactory[Player] {
  override def defaultInstance: Player = ???

  override val ids: List[Int] = List(10)

  override def fromId(id: Int): Player = ???
}
