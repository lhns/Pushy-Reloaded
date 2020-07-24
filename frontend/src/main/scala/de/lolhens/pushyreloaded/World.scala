package de.lolhens.pushyreloaded

import de.lolhens.pushyreloaded.tile._
import org.scalajs.dom
import org.scalajs.dom.html.Canvas

import scala.annotation.tailrec
import scala.util.chaining._

class World private(size: Vec2i,
                    private var worldTiles: Set[(Vec2i, TileInstance)]) {
  private var _changes: Map[(Vec2i, TileInstance), TileInstance] = Map.empty

  private def addChanged(pos: Vec2i, tile: TileInstance, newTile: TileInstance): Unit =
    if (tile != newTile) _changes = _changes + ((pos, tile) -> newTile)

  @tailrec
  final def getChanged(pos: Vec2i, tile: TileInstance): TileInstance =
    _changes.get(pos -> tile) match {
      case Some(tile) => getChanged(pos, tile)
      case None => tile
    }

  final def flushChanges(): Unit = _changes = Map.empty

  def list: Seq[(Vec2i, TileInstance)] =
    worldTiles.toSeq

  def list[Instance <: TileInstance](factory: TileFactory[Instance]): Seq[(Vec2i, Instance)] =
    worldTiles.iterator.flatMap(e => e._2.as(factory).map((e._1, _))).toSeq

  def get(pos: Vec2i): Seq[TileInstance] =
    worldTiles.iterator.filter(_._1 == pos).map(_._2).toSeq

  def get[Instance <: TileInstance](pos: Vec2i, factory: TileFactory[Instance]): Seq[Instance] =
    worldTiles.iterator.filter(_._1 == pos).flatMap(_._2.as(factory)).toSeq

  def add(pos: Vec2i, tile: TileInstance): Unit =
    if (tile != Background) {
      worldTiles = worldTiles + (pos -> tile)
    }

  def moveTo(pos: Vec2i, tile: TileInstance, newPos: Vec2i): Boolean =
    remove(pos, tile).tap(if (_) add(newPos, tile))

  def move(pos: Vec2i, tile: TileInstance, f: Vec2i => Vec2i): Boolean =
    moveTo(pos, tile, f(pos))

  def change(pos: Vec2i, tile: TileInstance, newTile: TileInstance): Boolean =
    remove(pos, tile).tap(if (_) {
      addChanged(pos, tile, newTile)
      add(pos, newTile)
    })

  def remove(pos: Vec2i, tile: TileInstance): Boolean = {
    val (removedWorldTiles, newWorldTiles) = worldTiles.partition(e => e._1 == pos && e._2 == tile)
    worldTiles = newWorldTiles
    removedWorldTiles.nonEmpty
  }

  def playerMove(direction: Direction): Unit = {
    list(Player).foreach(e => e._2.move(this, e._1, direction))
  }

  def render(canvas: Canvas): Unit = {
    canvas.width = size.x * TileInstance.size.x
    canvas.height = size.y * TileInstance.size.y

    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    for {
      y <- 0 until size.y
      x <- 0 until size.x
      pos = Vec2i(x, y)
    } {
      val renderPos = pos.map(_ * TileInstance.size.x, _ * TileInstance.size.y)
      val sortedTiles: Seq[TileInstance] = (Background +: get(pos)).sortBy(_.zIndex)
      sortedTiles.foreach(e => e.render(ctx, renderPos))
    }
  }
}

object World {
  def apply(size: Vec2i): World =
    new World(size, Set.empty)
}
