package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.Image

trait TileFactory[+Instance <: TileInstance] {
  def unapply(tile: TileInstance): Option[Instance] = tile.as(this)

  def variants: Seq[Instance]

  def idVariants: Seq[Instance] = variants

  final protected def cached[A >: Instance](instance: A): A =
    variants.find(_ == instance).getOrElse(instance)

  def fromId(id: Int): Option[Instance] = idVariants.find(_.id == id)
}

object TileFactory {
  val tiles: Seq[TileFactory[TileInstance]] = Seq(
    Background,
    Ball,
    BallTarget,
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
    FarMove,
    ColorChanger,
    ColorChangerTarget
  )

  val loadedImages: Seq[Image] = tiles.flatMap(_.variants).map(_.image)

  def fromId(id: Int): Option[TileInstance] =
    tiles.collectFirst(Function.unlift(_.fromId(id)))
}
