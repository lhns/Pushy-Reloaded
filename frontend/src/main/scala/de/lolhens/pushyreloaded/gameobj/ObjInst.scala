package de.lolhens.pushyreloaded.gameobj

import de.lolhens.pushyreloaded.Vec2i

trait ObjInst {
  type Type <: ObjType
  val objType: Type
  var props: Type#Props
  var pos: Vec2i
}

object ObjInst {
  case class Aux[T <: ObjType](objType: T, props: T#Props, pos: Vec2i) extends ObjInst {
    type Type = T
  }
}
