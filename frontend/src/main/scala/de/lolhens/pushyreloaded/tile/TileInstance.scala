package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.{Image, Physics, Vec2i, World}
import org.scalajs.dom

trait TileInstance {
  type Self <: TileInstance

  def factory: TileFactory[Self]

  def image: Image

  def physics: Physics

  def zIndex: Int = physics match {
    case Physics.Empty => 0
    case Physics.Solid => 1
    case Physics.Pushable => 2
  }

  def update(world: World, pos: Vec2i): Unit = ()

  def render(ctx: dom.CanvasRenderingContext2D,
             pos: Vec2i): Unit = {
    val image = this.image
    if (image.isReady) {
      ctx.drawImage(image.element, pos.x, pos.y, image.element.width, image.element.height)
    }
  }
}

object TileInstance {
  val size: Vec2i = Vec2i(32, 32)
}
