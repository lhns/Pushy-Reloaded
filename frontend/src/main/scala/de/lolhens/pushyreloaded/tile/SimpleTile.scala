package de.lolhens.pushyreloaded.tile

trait SimpleTile[Instance <: TileInstance] extends TileFactory[Instance] with TileInstance {
  override type Self = Instance

  override def factory: TileFactory[Instance] = this

  def self: Instance

  override def variants: Seq[Instance] = Seq(self)
}
