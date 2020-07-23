package de.lolhens.pushyreloaded.tile

trait TileFactory[Instance <: TileInstance] {
  def defaultInstance: Instance

  val ids: List[Int]

  def fromId(id: Int): Instance
}
