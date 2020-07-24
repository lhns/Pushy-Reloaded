package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Pushable}

sealed trait BoxTarget extends SimpleTile[BoxTarget] {
  override val image: Image = Image("/assets/images/18.bmp")

  override val pushable: Pushable = Pushable.Empty

  override val ids: List[Int] = List(18)

  override def fromId(id: Int): BoxTarget = this

  override def variants: Seq[BoxTarget] = Seq(this)
}

object BoxTarget extends BoxTarget
