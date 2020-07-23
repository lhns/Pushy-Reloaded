package de.lolhens.pushyreloaded.tile

trait LegacyTile[Instance <: TileInstance] {
  self: TileFactory[Instance] =>

  val ids: List[Int]

  def fromId(id: Int): Instance
}
