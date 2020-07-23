package de.lolhens.pushyreloaded.gameobj

import de.lolhens.pushyreloaded.gameobj.DirectionalGameObj.DirectionProps
import de.lolhens.pushyreloaded.{Direction, Image, Physics}

object Player extends ObjType with DirectionalGameObj {

  case class Props(direction: Direction) extends DirectionProps

  override val id: Int = 10
  override val image: Image = Image("/assets/images/10.png")
  override val physics: Physics = Physics.Solid
}
