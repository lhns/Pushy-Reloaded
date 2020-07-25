package de.lolhens.pushyreloaded.tile

trait TileFactory[+Instance <: TileInstance] {
  def unapply(tile: TileInstance): Option[Instance] = tile.as(this)

  def variants: Seq[Instance]

  final protected def cached[A >: Instance](instance: A): A =
    variants.find(_ == instance).getOrElse(instance)

  def fromId(id: Int): Option[Instance] = variants.find(_.id == id)
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
    Apple,
    ReverseMove,
    Projectile,
    Transformer,
    ProjectileTarget,
    Light,
    Key,
    Lock,
    FarMove
  )

  def fromId(id: Int): Option[TileInstance] =
    tiles.collectFirst(Function.unlift(_.fromId(id)))
}
