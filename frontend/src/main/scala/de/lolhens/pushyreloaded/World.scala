package de.lolhens.pushyreloaded

import de.lolhens.pushyreloaded.World.ObjInWorld
import de.lolhens.pushyreloaded.gameobj.{Background, ObjType, Player}
import org.scalajs.dom
import org.scalajs.dom.html.Canvas

class World private(size: Vec2i,
                    private var objects: Seq[ObjInWorld]) {
  add(Vec2i(1, 1), Player(Player.Props(Direction.Up)))

  def list()

  def get(pos: Vec2i): List[ObjType.Inst] = {
    objects.iterator.filter(_.pos == pos).map(_.inst).filterNot(_.gameObj == Background).toList
  }

  def add(pos: Vec2i, obj: ObjType.Inst): Unit = {
    objects = new ObjInWorld(obj, pos) +: objects
  }

  def remove(obj: ObjType.Inst): Unit = {
    objects = objects.filterNot(_.inst eq obj)
  }

  def playerMove(direction: Direction): Unit = {
    for {
      y <- 0 until size.y
      x <- 0 until size.x
      pos = Vec2i(x, y)
    } {
      objects(pos.x)(pos.y) = get(pos).map {
        case inst@Player(props) =>
          inst.withProps(Player.Props(direction).asInstanceOf[inst.gameObj.Props])

        case e => e
      }
    }
  }

  def render(canvas: Canvas): Unit = {
    canvas.width = size.x * ObjType.size.x
    canvas.height = size.y * ObjType.size.y

    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    for {
      y <- 0 until size.y
      x <- 0 until size.x
      pos = Vec2i(x, y)
    } {
      val renderPos = pos.map(_ * ObjType.size.x, _ * ObjType.size.y)
      (Background() +: get(pos)).sortBy(_.gameObj.zIndex).foreach(e => e.gameObj.render(ctx, renderPos, e.props))
    }
  }
}

object World {
  def apply(size: Vec2i): World =
    new World(size, Seq.empty)

  class ObjInWorld(val inst: ObjType.Inst,
                   private var _pos: Vec2i) {
    def pos: Vec2i = _pos

    def moveTo(pos: Vec2i): Unit = _pos = pos
  }

}
