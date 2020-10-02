package de.lolhens.pushyreloaded

import de.lolhens.pushyreloaded.tile._
import monix.execution.Scheduler.Implicits.global
import org.scalajs.dom
import org.scalajs.dom.html.Canvas

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.util.chaining._

object Main {
  private val canvas = dom.document.getElementById("canvas").asInstanceOf[Canvas]
  var world: World = World.empty(Vec2i(20, 12))

  var currentLevel: Int = 1

  def main(args: Array[String]): Unit = {
    loadCurrentLevel()

    dom.window.addEventListener("keydown", (e: dom.KeyboardEvent) => {
      e.key match {
        case "Enter" => restart()
        case "n" => next()
        case "b" => prev()
        case " " =>
          world.list(Player).foreach {
            case (pos, player) if player.attributes.get(Projectile.ProjectileAttribute) =>
              world.remove(pos, ProjectileCarryAnimation)
              player.changeAttributes(_.put(Projectile.ProjectileAttribute)(false))
              world.add(pos, ProjectileThrowAnimation(player.direction))

            case _ =>
          }
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

  dom.window.addEventListener("touchend", (e: dom.TouchEvent) => {
    if (dom.document.elementFromPoint(
      e.changedTouches(0).pageX,
      e.changedTouches(0).pageY
    ).id != "restart") {
      e.preventDefault()
      val (x, y) = e.changedTouches(0).pipe(e => (e.clientX, e.clientY))
      val (width, height) = dom.window.document.body.pipe(e => (e.clientWidth, e.clientHeight))
      val (relX, relY) = (x / width, y / height)
      if (relY <= 0.5) {
        if (relX <= relY) {
          world.playerMove(Direction.Left)
        } else if (relX >= 1 - relY) {
          world.playerMove(Direction.Right)
        } else {
          world.playerMove(Direction.Up)
        }
      } else {
        if (relX <= 1 - relY) {
          world.playerMove(Direction.Left)
        } else if (relX >= relY) {
          world.playerMove(Direction.Right)
        } else {
          world.playerMove(Direction.Down)
        }
      }
    }
  })

  dom.document.getElementById("restart").addEventListener("click", (e: dom.Event) => {
    restart()
  })

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
    add(Vec2i(5, 4), BallTarget(Ball.Color.Red))
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
    add(Vec2i(12, 16), Projectile)
    add(Vec2i(12, 17), ProjectileTarget)
    add(Vec2i(14, 16), Transformer)
    add(Vec2i(3, 16), ColorChanger(ColorChanger.Color.Red))
    add(Vec2i(5, 10), ColorChangerTarget(ColorChanger.Color.Red))
    add(Vec2i(16, 16), Apple)
    add(Vec2i(17, 16), Light(Light.State.New))
    add(Vec2i(18, 16), Light(Light.State.New))
    add(Vec2i(19, 16), Light(Light.State.New))
    add(Vec2i(19, 15), Light(Light.State.New))
    add(Vec2i(19, 14), Light(Light.State.New))
    add(Vec2i(5, 13), ShadowPlayer(Direction.Up))
    //add(Vec2i(5, 13), Player(Direction.Up))
    add(size.map(_ - 2, _ - 2), House)

    world
  }


  private def loadCurrentLevel(): Unit = {
    println(s"current level: $currentLevel")
    if (currentLevel == 0) world = testWorld
    else Resource.level(s"$currentLevel.lev").foreach(world = _)
  }

  def restart(): Unit =
    loadCurrentLevel()

  @JSExportTopLevel("setLevel")
  def setLevel(level: Int): Unit = {
    currentLevel = level
    loadCurrentLevel()
  }

  def next(): Unit = {
    currentLevel += 1
    loadCurrentLevel()
  }

  def prev(): Unit = {
    currentLevel -= 1
    loadCurrentLevel()
  }

  def update(d: Double): Unit = {
    world.update(d)
  }

  def render(): Unit = {
    world.render(canvas)
  }

  def loop(): Unit = {
    var prev = js.Date.now()
    // The main game loop
    val gameLoop = () => {
      val now = js.Date.now()
      val delta = now - prev

      update(delta / 1000)
      render()

      prev = now
    }

    dom.window.setInterval(gameLoop, 1)
  }
}
