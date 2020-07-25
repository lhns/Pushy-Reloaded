package de.lolhens.pushyreloaded.tile

trait SimpleTile[Instance <: TileInstance] extends TileFactory[Instance] with TileInstance {
  override type Self = Instance

  override def factory: TileFactory[Instance] = this

  def id: Int

  def self: Instance

  override lazy val ids: List[Int] = List(id)

  override def fromId(id: Int): Instance = self

  override def variants: Seq[Instance] = Seq(self)
}
