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

  def image: Image

  def move(world: World,
           pos: Vec2i,
           direction: Direction): Boolean = {
    val offset = pos.offset(direction)
    val targets = world.list(offset).map(_.instance.pushable(world, offset, direction, this))
    if (targets.forall(_._1.isMovableTo)) {
      targets.foreach(_._2.apply())
      world.get(pos, factory).find(_.instance == this).foreach(_.moveTo(offset))
      true
    } else
      false
  }

  private final def pushableIfAll(world: World,
                                  pos: Vec2i,
                                  direction: Direction,
                                  by: TileInstance,
                                  pushableTarget: Pushable => Boolean): (Pushable, () => Unit) = {
    val offset = pos.offset(direction)
    val targets = world.list(offset).map(_.instance.pushable(world, offset, direction, this))
    if (targets.forall(e => pushableTarget(e._1))) {
      (Pushable.Pushable.withAction {
        targets.foreach(_._2.apply())
        world.get(pos, factory).find(_.instance == this).foreach(_.moveTo(offset))
      })
    } else {
      Pushable.Blocked.withoutAction
    }
  }

  protected final def pushableSingle(world: World,
                                     pos: Vec2i,
                                     direction: Direction,
                                     by: TileInstance): (Pushable, () => Unit) =
    pushableIfAll(world, pos, direction, by, _.isEmpty)

  protected final def pushableChain(world: World,
                                    pos: Vec2i,
                                    direction: Direction,
                                    by: TileInstance): (Pushable, () => Unit) =
    pushableIfAll(world, pos, direction, by, _.isMovableTo)

  def pushable: Pushable

  def pushable(world: World,
               pos: Vec2i,
               direction: Direction,
               by: TileInstance): (Pushable, () => Unit) = {
    pushable match {
      case Pushable.Pushable =>
        pushableSingle(world, pos, direction, by)

      case pushable =>
        pushable.withoutAction
    }
  }

  def zIndex: Int = pushable match {
    case Pushable.Empty => 0
    case Pushable.Solid => 1
    case Pushable.Pushable | Pushable.Blocked => 2
  }

  def missionComplete(world: World, pos: Vec2i): Boolean = true

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
