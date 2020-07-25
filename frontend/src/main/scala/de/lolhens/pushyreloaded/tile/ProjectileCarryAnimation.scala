package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.Image

sealed trait ProjectileCarryAnimation extends VisualInstance with VisualFactory[ProjectileCarryAnimation] {
  override type Self = ProjectileCarryAnimation

  override def factory: TileFactory[ProjectileCarryAnimation] = ProjectileCarryAnimation

  override val image: Image = Image("/assets/images/107.png")

  override val zIndex: Int = 15
}

object ProjectileCarryAnimation extends ProjectileCarryAnimation
