package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._
import org.scalajs.dom

trait TileInstance {
  type Self <: TileInstance

  def factory: TileFactory[Self]

  final def is[Instance <: TileInstance](factory: TileFactory[Instance]): Boolean = this.factory == factory

  final def as[Instance <: TileInstance](factory: TileFactory[Instance]): Option[Instance] = this match {
    case instance: Instance@unchecked if this.factory == factory => Some(instance)
    case _ => None
  }

  def id: Int

  protected def defaultImageAsset(extension: String = "bmp"): Image = Image(s"/assets/images/$id.$extension")

  def image: Image

  def image(world: World, pos: Vec2i): Image = image

  def move(world: World,
           pos: Vec2i,
           direction: Direction): Boolean = {
    val offset = pos.offset(direction)
    val targets = world.get(offset).map(_.pushable(world, offset, direction, this, pos))
    if (targets.forall(_._1.isMovableTo)) {
      targets.foreach(_._2.apply())
      world.moveTo(pos, this, offset)
      world.flushChanges()
      true
    } else
      false
  }

  def zIndex: Int = pushable match {
    case Pushable.Empty => 0
    case Pushable.Solid => 1
    case Pushable.Pushable | Pushable.Blocked => 2
  }

  def isEmpty: Boolean = false

  def pushable: Pushable

  def pushable(world: World,
               pos: Vec2i,
               direction: Direction,
               by: TileInstance,
               byPos: Vec2i): (Pushable, () => Unit) = pushable match {
    case Pushable.Pushable =>
      pushableSingle(world, pos, direction)

    case pushable =>
      pushable.withoutAction
  }


  protected final def pushableSingle(world: World,
                                     pos: Vec2i,
                                     direction: Direction): (Pushable, () => Unit) =
    pushableIfAll(world, pos, direction, _.isEmpty)

  protected final def pushableChain(world: World,
                                    pos: Vec2i,
                                    direction: Direction): (Pushable, () => Unit) =
    pushableIfAll(world, pos, direction, _.isMovableTo)

  private final def pushableIfAll(world: World,
                                  pos: Vec2i,
                                  direction: Direction,
                                  pushableTarget: Pushable => Boolean): (Pushable, () => Unit) = {
    val offset = pos.offset(direction)
    val targets = world.get(offset).map(_.pushable(world, offset, direction, this, pos))
    if (targets.forall(e => pushableTarget(e._1)))
      Pushable.Pushable.withAction {
        targets.foreach(_._2.apply())
        val changed = world.getChanged(pos, this)
        world.moveTo(pos, changed, offset)
      }
    else
      Pushable.Blocked.withoutAction
  }

  def missionComplete(world: World, pos: Vec2i): Boolean = true

  def addedToWorld(world: World, pos: Vec2i, moved: Boolean): Unit = ()

  def removedFromWorld(world: World, pos: Vec2i, moved: Boolean): Unit = ()

  def update(world: World, pos: Vec2i): Unit = ()

  def render(world: World,
             pos: Vec2i,
             ctx: dom.CanvasRenderingContext2D,
             renderPos: Vec2i): Unit = {
    val image = this.image(world, pos)
    if (image.isReady)
      ctx.drawImage(image.element, renderPos.x, renderPos.y, image.element.width, image.element.height)
  }
}

object TileInstance {
  val size: Vec2i = Vec2i(32, 32)
}
