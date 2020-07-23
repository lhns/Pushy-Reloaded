package de.lolhens.pushyreloaded.gameobj

import de.lolhens.pushyreloaded.{Image, Physics, Vec2i}

object Background extends ObjType {
  override type Props = Unit
  override val id: Int = 0
  override val image: Image = Image("/assets/images/0.bmp")
  override val physics: Physics = Physics.Empty
  override val zIndex: Int = Int.MinValue

  override type Inst = this.type

  override def apply(pos: Vec2i, props: Unit): Background = ???
}