package de.lolhens.pushyreloaded

import java.io.ByteArrayInputStream

import de.lolhens.pushyreloaded.tile._
import org.scalajs.dom
import org.scalajs.dom.html.Canvas

import scala.scalajs.js
import scala.scalajs.js.typedarray.{ArrayBuffer, Uint8Array}

object Main {
  private val canvas = dom.document.getElementById("canvas").asInstanceOf[Canvas]
  var world: World = World.empty(Vec2i(20, 12))

  var currentLevel: Int = 0

  def main(args: Array[String]): Unit = {
    loadCurrentLevel()

    dom.window.addEventListener("keydown", (e: dom.KeyboardEvent) => {
      e.key match {
        case "Enter" => restart()
        case "n" => next()
        case "b" => prev()
        case key =>
          Option(key match {
            case "w" | "ArrowUp" => Direction.Up
            case "a" | "ArrowLeft" => Direction.Left
            case "s" | "ArrowDown" => Direction.Down
            case "d" | "ArrowRight" => Direction.Right
            case _ => null
          }).foreach(world.playerMove)
      }
    }, useCapture = false)

    loop()
  }

  def testWorld: World = {
    val size = Vec2i(30, 20)
    val world = World.empty(size)
    import world._

    ((0 until size.x).map(Vec2i(_, 0)) ++
      (0 until size.y).map(Vec2i(0, _)) ++
      (0 until size.x).map(Vec2i(_, size.y - 1)) ++
      (0 until size.y).map(Vec2i(size.x - 1, _))).foreach(add(_, Wall))

    add(Vec2i(1, 1), Player(Direction.Up))
    add(Vec2i(3, 1), Teleporter)
    add(Vec2i(6, 1), Stamp)
    add(Vec2i(2, 2), Ball(Ball.Color.Red))
    add(Vec2i(3, 2), Ball(Ball.Color.Green))
    add(Vec2i(5, 4), BallHole(Ball.Color.Red))
    add(Vec2i(7, 4), BallInk(Ball.Color.Red))
    add(Vec2i(8, 6), Box)
    add(Vec2i(10, 6), BoxTarget)
    add(Vec2i(12, 6), Button)
    add(Vec2i(14, 6), SecretDoor)
    add(Vec2i(16, 6), Teleporter)
    add(Vec2i(16, 8), MagicBlock)
    add(Vec2i(18, 8), MagicBlock)
    add(Vec2i(17, 9), MagicBlock)
    add(Vec2i(21, 15), MotionSensor(MotionSensor.State.Inactive))
    add(Vec2i(23, 15), MotionSensor(MotionSensor.State.Inactive))
    add(Vec2i(5, 16), Key)
    add(Vec2i(5, 17), Lock)
    add(Vec2i(5, 18), Lock)
    add(Vec2i(8, 16), ReverseMove)
    add(Vec2i(9, 16), FarMove)
    add(size.map(_ - 2, _ - 2), House)

    world
  }


  def request(url: String, response: Array[Byte] => ()): Unit = {
    val xhr = new dom.XMLHttpRequest()
    xhr.open("GET", url)
    xhr.responseType = "arraybuffer"
    xhr.onload = { (e: dom.Event) =>
      if (xhr.status == 200) {
        val uint8Array = new Uint8Array(xhr.response.asInstanceOf[ArrayBuffer])
        val array = new Array[Byte](uint8Array.byteLength)
        for (i <- array.indices)
          array(i) = uint8Array(i).toByte
        response(array)
      }
    }
    xhr.send()
  }

  private def loadCurrentLevel(): Unit = {
    if (currentLevel == 0) world = testWorld
    else
      request(s"/assets/level/$currentLevel.lev", { bytes =>
        val inputStream = new ByteArrayInputStream(bytes)
        world = PushyLevelFormat.loadFromInputStream(inputStream)
      })
  }

  def restart(): Unit =
    loadCurrentLevel()

  def next(): Unit = {
    currentLevel += 1
    loadCurrentLevel()
  }

  def prev(): Unit = {
    currentLevel -= 1
    loadCurrentLevel()
  }

  def update(): Unit = ()

  def render(d: Double): Unit = {
    world.render(canvas, d)
  }

  def loop(): Unit = {
    var prev = js.Date.now()
    // The main game loop
    val gameLoop = () => {
      val now = js.Date.now()
      val delta = now - prev

      update()
      render(delta / 1000)

      prev = now
    }

    dom.window.setInterval(gameLoop, 1)
  }
}
