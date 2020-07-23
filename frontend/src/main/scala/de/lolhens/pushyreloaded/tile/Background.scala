package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Physics}

sealed trait Background extends SimpleTile[Background]

object Background extends Background {
  //override type Props = Unit
  //override val id: Int = 0
  override val image: Image = Image("/assets/images/0.bmp")
  override val physics: Physics = Physics.Empty
  override val zIndex: Int = Int.MinValue

  //override type Inst = this.type

  //override def apply(pos: Vec2i, props: Unit): Background = ???

  override val ids: List[Int] = List(0)

  override def fromId(id: Int): Background.type = this

  override def defaultInstance: Background.type = this
}