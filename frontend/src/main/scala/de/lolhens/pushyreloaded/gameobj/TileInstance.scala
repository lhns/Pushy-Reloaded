package de.lolhens.pushyreloaded.gameobj

import de.lolhens.pushyreloaded.{Vec2i, World}

trait TileInstance {
  def update(world: World, pos: Vec2i): Unit
}
