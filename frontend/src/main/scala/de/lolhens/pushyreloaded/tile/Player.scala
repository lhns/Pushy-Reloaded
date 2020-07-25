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
    this.direction = direction
    super.move(world, pos, direction).tap(if (_) {
      val offset = pos.offset(direction)
      if (attributes.get(Stamp.StampAttribute) && world.isEmpty(offset, except = _.is(Player)))
        world.add(offset, Stamp)
    })
  }
}

object Player extends TileFactory[Player] {
  def apply(direction: Direction): Player = new Player(direction)

  private val image: Image = Image("/assets/images/10.png")

  override def variants: Seq[Player] = Seq()

  override val ids: List[Int] = List(10)

  override def fromId(id: Int): Player = Player(Direction.Up)

}
