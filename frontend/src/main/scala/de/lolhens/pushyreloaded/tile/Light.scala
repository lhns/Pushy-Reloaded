package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.Attributes.Attribute
import de.lolhens.pushyreloaded._
import de.lolhens.pushyreloaded.tile.Light.State

case class Light private(state: Light.State) extends TileInstance {
  override type Self = Light

  override def factory: TileFactory[Light] = Light

  def withState(state: Light.State): Light = Light(state)

  def mapState(f: Light.State => Light.State): Light = withState(f(state))

  override lazy val id: Int = state match {
    case State.New => 30
    case State.Off => 31
    case State.On(_) | State.Blinking => 29
  }

  override lazy val image: Image = defaultImageAsset()

  override def image(world: World, pos: Vec2i): Image = state match {
    case State.Blinking =>
      if ((world.time - world.attributes.get(Light.lastUpdatedAttribute)) % (Light.blinkInterval * 2) > Light.blinkInterval)
        image
      else
        Light(State.Off).image

    case _ =>
      super.image(world, pos)
  }

  override val pushable: Pushable = Pushable.Empty

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    if (!state.isOn)
      by match {
        case Player(player) if player.attributes.get(Transformer.ChargedAttribute) =>
          Pushable.Empty.withAction {
            player.changeAttributes(_.put(Transformer.ChargedAttribute)(false))
            world.change(pos, this, this.mapState(_.on(None)))
          }

        case _ =>
          super.pushable(world, pos, direction, by, byPos)
      }
    else
      super.pushable(world, pos, direction, by, byPos)

  private def shouldUpdate(world: World): Boolean = {
    val lightsLastUpdated = world.attributes.get(Light.lastUpdatedAttribute)

    if (world.time - lightsLastUpdated >= Light.updateInterval) {
      world.changeAttributes(_.put(Light.lastUpdatedAttribute)(world.time))
      true
    } else if (world.time == lightsLastUpdated)
      true
    else
      false
  }

  override def update(world: World, pos: Vec2i): Unit =
    if (shouldUpdate(world)) state match {
      case State.Blinking =>
        world.change(pos, this, this.mapState(_.off))

      case State.On(sourceDirection) =>
        val directions = Direction.values.filterNot(sourceDirection.contains) ++ sourceDirection.toList
        val nextLight = directions.collectFirst(Function.unlift { direction =>
          val offset = pos.offset(direction)
          if (world.get(offset).exists(_.pushable == Pushable.Pushable))
            None
          else
            world.get(offset, Light).find(!_.state.isOn).map((direction, _))
        })

        nextLight match {
          case Some((direction, tile)) =>
            world.change(pos, this, this.mapState(_.off))
            world.change(pos.offset(direction), tile, tile.mapState(_.on(Some(direction.opposite))))

          case None =>
            world.change(pos, this, this.withState(State.Blinking))
        }

      case _ =>
    }

  override def missionComplete(world: World, pos: Vec2i): Boolean =
    !state.isOn
}

object Light extends TileFactory[Light] {
  def apply(state: Light.State): Light = cached(new Light(state))

  override val variants: Seq[Light] = State.values.map(new Light(_))
  override val idVariants: Seq[Light] = State.idValues.map(new Light(_))

  val updateInterval: Double = 1

  val blinkInterval: Double = 0.125

  private val lastUpdatedAttribute: Attribute[Double] = Attribute("lights_last_update", () => 0)

  sealed abstract class State(val index: Int) {
    def isOn: Boolean

    def off: State

    def on(sourceDirection: Option[Direction]): State = State.On(sourceDirection)
  }

  object State {

    case object New extends State(0) {
      override def isOn: Boolean = false

      override def off: State = this
    }

    case object Off extends State(1) {
      override def isOn: Boolean = false

      override def off: State = this
    }

    case class On(sourceDirection: Option[Direction]) extends State(2) {
      override def isOn: Boolean = true

      override def off: State = Off
    }

    case object Blinking extends State(3) {
      override def isOn: Boolean = false

      override def off: State = Off
    }

    val values: List[State] = List(New, Off, On(None), Blinking) ++ Direction.values.map(e => On(Some(e)))
    val idValues: List[State] = List(New, Off, On(None))
  }

}


