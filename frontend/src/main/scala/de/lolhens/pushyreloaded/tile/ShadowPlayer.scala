package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._

class ShadowPlayer private(private var _direction: Direction) extends TileInstance with Directional {
  override type Self = ShadowPlayer

  override def factory: TileFactory[ShadowPlayer] = ShadowPlayer

  override def direction: Direction = _direction

  private def setDirection(direction: Direction): Unit = _direction = direction

  override val id: Int = ShadowPlayer.id

  override def image: Image = ShadowPlayer.image

  override val zIndex: Int = 8

  override val pushable: Pushable = Pushable.Pushable

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    super.pushable(world, pos, direction, by, byPos) match {
      case (Pushable.Pushable, action) =>
        Pushable.Pushable.withAction {
          setDirection(direction)
          action()
        }

      case e => e
    }
}

object ShadowPlayer extends TileFactory[ShadowPlayer] {
  def apply(direction: Direction): ShadowPlayer = new ShadowPlayer(direction)

  val id: Int = 40

  private val image: Image = Resource.image(s"$id.png")

  override val variants: Seq[ShadowPlayer] = Seq()

  override def fromId(id: Int): Option[ShadowPlayer] = Option.when(id == this.id)(ShadowPlayer(Direction.Up))
}
