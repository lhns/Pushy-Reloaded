package de.lolhens.pushyreloaded.tile

trait TileFactory[+Instance <: TileInstance] {
  def unapply(tile: TileInstance): Option[Instance] = tile.as(this)

  def variants: Seq[Instance]

  final protected def cached[A >: Instance](instance: A): A =
    variants.find(_ == instance).getOrElse(instance)

  val ids: List[Int]

  def fromId(id: Int): Instance
}

object TileFactory {
  val tiles: Seq[TileFactory[TileInstance]] = Seq(
    Background,
    Ball,
    BallHole,
    House,
    Teleporter,
    Box,
    Player,
    Stamp,
    Wall,
    Button,
    SecretDoor,
    BallInk,
    BoxTarget,
    MagicBlock,
    MotionSensor,
    Apple
  )

  def fromId(id: Int): Option[TileInstance] =
    tiles.find(_.ids.contains(id)).map(_.fromId(id))
}
