package de.lolhens.pushyreloaded

import org.scalajs.dom
import org.scalajs.dom.html.Canvas

import scala.scalajs.js

object Main {
  var world: World = _

  def main(args: Array[String]): Unit = {
    val canvas = dom.document.getElementById("canvas").asInstanceOf[Canvas]
    //val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    //canvas.width = (0.95 * dom.window.innerWidth).toInt
    //canvas.height = (0.95 * dom.window.innerHeight).toInt
    //dom.document.body.appendChild(canvas)

    world = World(Vec2i(20, 12))

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
