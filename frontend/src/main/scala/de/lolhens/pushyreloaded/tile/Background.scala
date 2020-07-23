package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Physics}

sealed trait Background extends SimpleTile[Background]

object Background extends Background {
  override val image: Image = Image("/assets/images/0.bmp")
  override val physics: Physics = Physics.Empty
  override val zIndex: Int = Int.MinValue

  override val ids: List[Int] = List(0)

  override def fromId(id: Int): Background.type = this

  override def variants: Seq[Background] = Seq(this)
}