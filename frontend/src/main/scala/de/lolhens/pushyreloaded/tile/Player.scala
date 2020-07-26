package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded.Attributes.WithAttributes
import de.lolhens.pushyreloaded._

import scala.util.chaining._

class Player private(private var _direction: Direction) extends TileInstance with Directional with WithAttributes {
  override type Self = Player

  override def factory: TileFactory[Player] = Player

  def direction: Direction = _direction

  private def setDirection(direction: Direction): Unit = _direction = direction

  override val id: Int = Player.id

  override def image: Image =
    if (attributes.get(Transformer.ChargedAttribute)) Player.chargedImage
    else Player.image

  override val zIndex: Int = 10

  override val pushable: Pushable = Pushable.Solid

  override def move(world: World, pos: Vec2i, direction: Direction): Boolean = {
    val newDirection =
      if (attributes.get(ReverseMove.ReverseMoveAttribute)) direction.opposite
      else direction

    this.setDirection(newDirection)

    def moveStep(): Boolean =
      world.list.find(_._2 == this).exists {
        case (pos, _) =>
          super.move(world, pos, newDirection).tap(if (_) {
            val offset = pos.offset(newDirection)

            if (attributes.get(Stamp.StampAttribute) && world.isEmpty(offset, except = _.is(Player)))
              world.add(offset, Stamp)
          })
      }


    if (attributes.get(FarMove.FarMoveAttribute))
      moveStep().tap(if (_) {
        while (moveStep()) ()
      })
    else
      moveStep()
  }

  override def addedToWorld(world: World, pos: Vec2i, moved: Boolean): Unit =
    if (attributes.get(Projectile.ProjectileAttribute))
      world.add(pos, ProjectileCarryAnimation)

  override def removedFromWorld(world: World, pos: Vec2i, moved: Boolean): Unit =
    if (attributes.get(Projectile.ProjectileAttribute))
      world.remove(pos, ProjectileCarryAnimation)
}

object Player extends TileFactory[Player] {
  def apply(direction: Direction): Player = new Player(direction)

  val id: Int = 10

  private val image: Image = Image(s"/assets/images/$id.png")
  private val chargedImage: Image = Image("/assets/images/124.png")

  override def variants: Seq[Player] = Seq()

  override def fromId(id: Int): Option[Player] = Option.when(id == this.id)(Player(Direction.Up))
}
