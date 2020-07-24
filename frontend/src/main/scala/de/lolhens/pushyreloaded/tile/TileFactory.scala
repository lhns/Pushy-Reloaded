package de.lolhens.pushyreloaded.tile

trait TileFactory[Instance <: TileInstance] {
  def unapply(tile: TileInstance): Option[Instance] = tile.as(this)

  def variants: Seq[Instance]

  final protected def cached(instance: Instance): Instance =
    variants.find(_ == instance).getOrElse(instance)

  val ids: List[Int]

  def fromId(id: Int): Instance
}
