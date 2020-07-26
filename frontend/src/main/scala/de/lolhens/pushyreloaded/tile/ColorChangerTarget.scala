package de.lolhens.pushyreloaded.tile

import de.lolhens.pushyreloaded._
import de.lolhens.pushyreloaded.tile.ColorChanger.Color

case class ColorChangerTarget private(color: Color) extends TileInstance {
  override type Self = ColorChangerTarget

  override def factory: TileFactory[ColorChangerTarget] = ColorChangerTarget

  override lazy val id: Int = color match {
    case Color.Red => 39
    case Color.Yellow => 116
    case Color.Blue => 117
    case Color.Green => 118
    case Color.Gray => 120
  }

  override lazy val image: Image = defaultImageAsset()

  override val pushable: Pushable = Pushable.Solid

  override def pushable(world: World, pos: Vec2i, direction: Direction, by: TileInstance, byPos: Vec2i): (Pushable, () => Unit) =
    by match {
      case Player(_) =>
        Pushable.Empty.withoutAction

      case ColorChanger(colorChanger) if colorChanger.nextColor == color =>
        Pushable.Empty.withoutAction

      case _ =>
        super.pushable(world, pos, direction, by, byPos)
    }

  override def missionComplete(world: World, pos: Vec2i): Boolean =
    world.get(pos, ColorChanger).nonEmpty
}

object ColorChangerTarget extends TileFactory[ColorChangerTarget] {
  def apply(color: Color): ColorChangerTarget = cached(new ColorChangerTarget(color))

  override val variants: Seq[ColorChangerTarget] = Color.values.map(new ColorChangerTarget(_))
}
