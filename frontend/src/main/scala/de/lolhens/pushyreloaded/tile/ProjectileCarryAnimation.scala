package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Resource}

sealed trait ProjectileCarryAnimation extends VisualInstance with VisualFactory[ProjectileCarryAnimation] {
  override type Self = ProjectileCarryAnimation

  override def factory: TileFactory[ProjectileCarryAnimation] = ProjectileCarryAnimation

  override val image: Image = Resource.image("107.png")

  override val zIndex: Int = Player.zIndex + 1
}

object ProjectileCarryAnimation extends ProjectileCarryAnimation
