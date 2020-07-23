package de.lolhens.pushyreloaded.gameobj

trait TileFactory[Instance <: TileInstance] {
  def defaultInstance: Instance

  val ids: List[Int]

  def fromId(id: Int): Instance
}
