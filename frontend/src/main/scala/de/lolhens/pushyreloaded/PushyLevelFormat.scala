package de.lolhens.pushyreloaded

import java.io.{DataInputStream, InputStream}

import de.lolhens.pushyreloaded.tile.TileFactory

object PushyLevelFormat {
  val levelSize: Vec2i = Vec2i(20, 12)

  def loadFromInputStream(inputStream: InputStream): World = {
    val stream = new DataInputStream(inputStream)

    val world = World.empty(levelSize)

    for {
      x <- 0 until levelSize.x
      y <- 0 until levelSize.y
      pos = Vec2i(x, y)
    } {
      if (!(x == 0 && y == 0)) {
        stream.skipBytes(125)
      }

      val ignore = stream.readByte() != 2
      stream.skipBytes(1)
      val id = stream.readByte() & 0xFF

      if (!ignore && id > 0) {
        TileFactory.fromId(id) match {
          case Some(tile) =>
            world.add(pos, tile)

          case None =>
            System.err.println(s"Ignoring unknown tile id: $id")
        }
      }
    }
    stream.close()
    inputStream.close()

    world
  }
}
