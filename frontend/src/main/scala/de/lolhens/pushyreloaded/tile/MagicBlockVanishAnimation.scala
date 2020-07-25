package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Vec2i, World}

class MagicBlockVanishAnimation private(delay: Double) extends VisualInstance {
  override type Self = MagicBlockVanishAnimation

  override def factory: TileFactory[MagicBlockVanishAnimation] = MagicBlockVanishAnimation

  override def image: Image = MagicBlock.image

  private var time: Double = -1

  override def update(world: World, pos: Vec2i): Unit =
    if (time == -1)
      time = world.time
    else if (world.time - time > delay)
      world.remove(pos, this)
}

object MagicBlockVanishAnimation extends VisualFactory[MagicBlockVanishAnimation] {
  def apply(delay: Double): MagicBlockVanishAnimation = new MagicBlockVanishAnimation(delay)
}
