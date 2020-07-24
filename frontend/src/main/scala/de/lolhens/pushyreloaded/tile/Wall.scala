package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Pushable}

sealed trait Wall extends SimpleTile[Wall] {
  override val image: Image = Image("/assets/images/12.bmp")

  override val pushable: Pushable = Pushable.Solid

  override val ids: List[Int] = List(12)

  override def fromId(id: Int): Wall = this

  override def variants: Seq[Wall] = Seq(this)
}

object Wall extends Wall
