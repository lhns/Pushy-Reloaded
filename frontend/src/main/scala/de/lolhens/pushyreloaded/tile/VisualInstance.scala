package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.Pushable

trait VisualInstance extends TileInstance {
  override def id: Int = 0

  override def isEmpty: Boolean = true

  override def pushable: Pushable = Pushable.Empty

  override def zIndex: Int = 2
}

trait VisualFactory[+Instance <: TileInstance] extends TileFactory[Instance] {
  override def variants: Seq[Instance] = List()

  override def idVariants: Seq[Instance] = List()
}
