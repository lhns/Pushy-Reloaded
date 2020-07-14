package de.lolhens.pushyreloaded.gameobj

import de.lolhens.pushyreloaded.Image
import de.lolhens.pushyreloaded.gameobj.GameObj.Physics

object Background extends GameObj {
  override type Props = Unit
  override val id: Int = 0
  override val image: Image = Image("/assets/images/0.bmp")
  override val physics: GameObj.Physics = Physics.Empty
  override val zIndex: Int = Int.MinValue
}