package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Pushable}

sealed trait Background extends SimpleTile[Background] {
  override val ids: List[Int] = List(0)

  override def fromId(id: Int): Background = this

  override val image: Image = Image("/assets/images/0.bmp")

  override val pushable: Pushable = Pushable.Empty

  override val zIndex: Int = Int.MinValue

  override def variants: Seq[Background] = Seq(this)
}

object Background extends Background
