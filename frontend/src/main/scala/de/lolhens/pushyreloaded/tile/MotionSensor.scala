package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._
import de.lolhens.pushyreloaded.tile.MotionSensor.State

case class MotionSensor private(state: MotionSensor.State) extends TileInstance {
  override type Self = MotionSensor

  override def factory: TileFactory[MotionSensor] = MotionSensor

  def withState(state: MotionSensor.State): MotionSensor = MotionSensor(state)

  override lazy val id: Int =
    if (state == State.Closed) 23
    else 20

  override lazy val image: Image = state match {
    case State.Inactive => Resource.image("20.bmp")
    case State.Active => Resource.image("109.bmp")
    case State.Closed => Resource.image("23.bmp")
  }

  override val pushable: Pushable = Pushable.Solid

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    state match {
      case State.Inactive | State.Active =>
        by match {
          case Player(_) =>
            Pushable.Empty.withAction {
              val others = world.list(MotionSensor).filterNot(e => e._1 == pos && e._2 == this)
              val othersActive = others.filter(_._2.state == State.Active)
              if (othersActive.nonEmpty) {
                ((pos -> this) +: othersActive).foreach(e => world.change(e._1, e._2, e._2.withState(State.Closed)))
              } else {
                world.change(pos, this, this.withState(State.Active))
              }
            }

          case _ =>
            Pushable.Empty.withoutAction
        }

      case State.Closed =>
        Pushable.Solid.withoutAction
    }

  override def addedToWorld(world: World, pos: Vec2i, moved: Boolean): Unit = {
    if (state == State.Active)
      world.add(pos, MotionSensorAnimation())
  }

  override def removedFromWorld(world: World, pos: Vec2i, moved: Boolean): Unit = {
    if (state == State.Active)
      world.get(pos, MotionSensorAnimation).foreach(world.remove(pos, _))
  }
}

object MotionSensor extends TileFactory[MotionSensor] {
  def apply(state: MotionSensor.State): MotionSensor = cached(new MotionSensor(state))

  override val variants: Seq[MotionSensor] = State.values.map(new MotionSensor(_))
  override val idVariants: Seq[MotionSensor] = State.idValues.map(new MotionSensor(_))

  sealed abstract class State(val index: Int)

  object State {

    case object Inactive extends State(0)

    case object Active extends State(1)

    case object Closed extends State(2)

    val values: List[State] = List(Inactive, Active, Closed)
    val idValues: List[State] = List(Inactive, Closed)
  }

}
