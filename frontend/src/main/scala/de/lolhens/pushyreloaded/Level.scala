package de.lolhens.pushyreloaded

import de.lolhens.pushyreloaded.gameobj.{Background, GameObj, Player}
import org.scalajs.dom
import org.scalajs.dom.html.Canvas

class Level(size: Vec2,
            var playerPos: Vec2,
            private val objects: Array[Array[List[GameObj.Inst]]]) {
  add(Vec2(1, 1), Player(Player.Props(Direction.Up)))

  def get(pos: Vec2): List[GameObj.Inst] = {
    objects.lift(pos.x).flatMap(_.lift(pos.y)).flatMap(Option(_)).toList.flatten.filterNot(_.gameObj == Background)
  }

  def add(pos: Vec2, obj: GameObj.Inst): Unit = {
    objects(pos.x)(pos.y) = obj +: get(pos)
  }

  def remove(pos: Vec2, obj: GameObj): Unit = {
    objects(pos.x)(pos.y) = get(pos).filterNot(_.gameObj == obj)
  }

  def playerMove(direction: Direction): Unit = {
    for {
      y <- 0 until size.y
      x <- 0 until size.x
      pos = Vec2(x, y)
    } {
      objects(pos.x)(pos.y) = get(pos).map {
        case inst@Player(props) =>
          inst.withProps(Player.Props(direction).asInstanceOf[inst.gameObj.Props])

        case e => e
      }
    }
  }

  def render(canvas: Canvas): Unit = {
    canvas.width = size.x * GameObj.size.x
    canvas.height = size.y * GameObj.size.y

    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    for {
      y <- 0 until size.y
      x <- 0 until size.x
      pos = Vec2(x, y)
    } {
      val renderPos = pos.map(_ * GameObj.size.x, _ * GameObj.size.y)
      (Background() +: get(pos)).sortBy(_.gameObj.zIndex).foreach(e => e.gameObj.render(ctx, renderPos, e.props))
    }
  }
}

object Level {
  def apply(size: Vec2): Level =
    new Level(size, Vec2.zero, Array.ofDim(size.x, size.y))
}
