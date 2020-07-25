package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.Attributes.WithAttributes
import de.lolhens.pushyreloaded._

import scala.util.chaining._

class Player private(var direction: Direction) extends TileInstance with Directional with WithAttributes {
  override type Self = Player

  override def factory: TileFactory[Player] = Player

  override def image: Image = Player.image

  override val zIndex: Int = 10

  override val pushable: Pushable = Pushable.Solid

  override def move(world: World, pos: Vec2i, direction: Direction): Boolean = {
    val newDirection =
      if (attributes.get(ReverseMove.ReverseMoveAttribute)) direction.opposite
      else direction

    this.direction = newDirection

    def moveStep(pos: Vec2i): Option[Vec2i] =
      super.move(world, pos, newDirection).pipe(if (_) {
        val offset = pos.offset(newDirection)

        if (attributes.get(Stamp.StampAttribute) && world.isEmpty(offset, except = _.is(Player)))
          world.add(offset, Stamp)

        Some(offset)
      } else
        None)

    if (attributes.get(FarMove.FarMoveAttribute))
      moveStep(pos).tap {
        case Some(newPos) => // TODO: might be teleported
          var pos = newPos
          while (moveStep(pos).map(pos = _).isDefined) ()
      }.isDefined
    else
      moveStep(pos).isDefined
  }
}

object Player extends TileFactory[Player] {
  def apply(direction: Direction): Player = new Player(direction)

  private val image: Image = Image("/assets/images/10.png")

  override def variants: Seq[Player] = Seq()

  override val ids: List[Int] = List(10)

  override def fromId(id: Int): Player = Player(Direction.Up)

}
