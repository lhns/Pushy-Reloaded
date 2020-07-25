package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Pushable, Vec2i, World}

sealed trait ProjectileTarget extends SimpleTile[ProjectileTarget] {
  override val id: Int = 28

  override def self: ProjectileTarget = this

  override val image: Image = defaultImageAsset()

  override val pushable: Pushable = Pushable.Pushable

  override def missionComplete(world: World, pos: Vec2i): Boolean = false
}

object ProjectileTarget extends ProjectileTarget
