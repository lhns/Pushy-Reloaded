package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.Image

sealed trait ProjectileThrowAnimation extends VisualInstance with VisualFactory[ProjectileThrowAnimation] {
  override type Self = ProjectileThrowAnimation

  override def factory: TileFactory[ProjectileThrowAnimation] = ProjectileThrowAnimation

  override val image: Image = Image("/assets/images/107.png")

  override val zIndex: Int = 15
}

object ProjectileThrowAnimation extends ProjectileThrowAnimation
