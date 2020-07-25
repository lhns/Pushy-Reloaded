package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.Pushable

trait VisualInstance extends TileInstance {
  override def isEmpty: Boolean = true

  override def pushable: Pushable = Pushable.Empty

  override def zIndex: Int = 3
}

trait VisualFactory[+Instance <: TileInstance] extends TileFactory[Instance] {
  override def variants: Seq[Instance] = List()

  override val ids: List[Int] = List()

  override def fromId(id: Int): Instance = throw new UnsupportedOperationException()
}
