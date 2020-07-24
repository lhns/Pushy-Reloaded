package de.lolhens.pushyreloaded

import de.lolhens.pushyreloaded.tile._
import org.scalajs.dom
import org.scalajs.dom.html.Canvas

import scala.scalajs.js

object Main {
  var world: World = _

  def testWorld: World = {
    val size = Vec2i(20, 12)
    val world = World(size)
    import world._

    ((0 until size.x).map(Vec2i(_, 0)) ++
      (0 until size.y).map(Vec2i(0, _)) ++
      (0 until size.x).map(Vec2i(_, size.y - 1)) ++
      (0 until size.y).map(Vec2i(size.x - 1, _))).foreach(add(_, Wall))
    add(Vec2i(1, 1), Player(Direction.Up))
    add(Vec2i(2, 2), Ball(Ball.Color.Red))
    add(Vec2i(3, 2), Ball(Ball.Color.Green))
    add(Vec2i(5, 4), BallHole(Ball.Color.Red))
    add(Vec2i(7, 4), BallInk(Ball.Color.Red))
    add(Vec2i(8, 6), Box)
    add(Vec2i(10, 6), BoxTarget)
    add(size.map(_ - 2, _ - 2), House)

    world
  }

  def main(args: Array[String]): Unit = {
    val canvas = dom.document.getElementById("canvas").asInstanceOf[Canvas]
    //val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    //canvas.width = (0.95 * dom.window.innerWidth).toInt
    //canvas.height = (0.95 * dom.window.innerHeight).toInt
    //dom.document.body.appendChild(canvas)

    world = testWorld

    dom.window.addEventListener("keydown", (e: dom.KeyboardEvent) => {
      Option(e.key match {
        case "w" | "ArrowUp" => Direction.Up
        case "a" | "ArrowLeft" => Direction.Left
        case "s" | "ArrowDown" => Direction.Down
        case "d" | "ArrowRight" => Direction.Right
        case _ => null
      }).foreach(world.playerMove)
    }, useCapture = false)

    def update(d: Double): Unit = {
      world.render(canvas)
      //if (bgImage.isReady) {
      //  ctx.drawImage(bgImage.element, 0, 0, bgImage.element.width, bgImage.element.height)
      //}
    }

    var prev = js.Date.now()
    // The main game loop
    val gameLoop = () => {
      val now = js.Date.now()
      val delta = now - prev

      update(delta / 1000)
      //render()

      prev = now
    }

    dom.window.setInterval(gameLoop, 1)
  }
}
