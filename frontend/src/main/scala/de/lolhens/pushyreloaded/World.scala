package de.lolhens.pushyreloaded

import de.lolhens.pushyreloaded.Attributes.WithAttributes
import de.lolhens.pushyreloaded.tile._
import org.scalajs.dom
import org.scalajs.dom.html.Canvas

import scala.annotation.tailrec
import scala.util.chaining._

class World private(size: Vec2i,
                    private var worldTiles: Set[(Vec2i, TileInstance)]) extends WithAttributes {
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

  private var _time: Double = 0

  def time: Double = _time

  def inside(pos: Vec2i): Boolean =
    pos.x >= 0 && pos.y >= 0 && pos.x < size.x && pos.y < size.y

  def list: Seq[(Vec2i, TileInstance)] =
    worldTiles.toSeq

  def list[Instance <: TileInstance](factory: TileFactory[Instance]): Seq[(Vec2i, Instance)] =
    worldTiles.iterator.flatMap(e => e._2.as(factory).map((e._1, _))).toSeq

  def get(pos: Vec2i): Seq[TileInstance] =
    worldTiles.iterator.filter(_._1 == pos).map(_._2).toSeq

  def get[Instance <: TileInstance](pos: Vec2i, factory: TileFactory[Instance]): Seq[Instance] =
    worldTiles.iterator.filter(_._1 == pos).flatMap(_._2.as(factory)).toSeq

  def isEmpty(pos: Vec2i, except: TileInstance => Boolean = _ => false): Boolean =
    get(pos).forall(e => except(e) || e.isEmpty)

  def add(pos: Vec2i, tile: TileInstance): Unit =
    add(pos, tile, moved = false)

  private def add(pos: Vec2i, tile: TileInstance, moved: Boolean): Unit =
    if (tile != Background) {
      worldTiles = worldTiles + (pos -> tile)
      tile.addedToWorld(this, pos, moved)
    }

  def remove(pos: Vec2i, tile: TileInstance): Boolean =
    remove(pos, tile, moved = false)

  private def remove(pos: Vec2i, tile: TileInstance, moved: Boolean): Boolean = {
    val (removedWorldTiles, newWorldTiles) = worldTiles.partition(e => e._1 == pos && e._2 == tile)
    worldTiles = newWorldTiles
    removedWorldTiles.foreach(e => e._2.removedFromWorld(this, e._1, moved))
    removedWorldTiles.nonEmpty
  }

  def moveTo(pos: Vec2i, tile: TileInstance, newPos: Vec2i): Boolean =
    remove(pos, tile, moved = true).tap(if (_) add(newPos, tile, moved = true))

  def move(pos: Vec2i, tile: TileInstance, f: Vec2i => Vec2i): Boolean =
    moveTo(pos, tile, f(pos))

  def change(pos: Vec2i, tile: TileInstance, newTile: TileInstance): Boolean =
    remove(pos, tile).tap(if (_) {
      addChanged(pos, tile, newTile)
      add(pos, newTile)
    })

  def playerMove(direction: Direction): Unit = {
    list(Player).foreach(e => e._2.move(this, e._1, direction))
  }

  def update(d: Double): Unit = {
    _time += d
    list.foreach(e => e._2.update(this, e._1))
  }

  def render(canvas: Canvas): Unit = {
    canvas.width = size.x * TileInstance.size.x
    canvas.height = size.y * TileInstance.size.y

    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    val tiles =
      for {
        y <- 0 until size.y
        x <- 0 until size.x
        pos = Vec2i(x, y)
        tile <- Background +: get(pos)
      } yield
        (pos, tile)

    tiles.sortBy(_._2.zIndex).foreach {
      case (pos, tile) =>
        val renderPos = pos.map(_ * TileInstance.size.x, _ * TileInstance.size.y)
        tile.render(this, pos, ctx, renderPos)
    }
  }
}

object World {
  def empty(size: Vec2i): World =
    new World(size, Set.empty)
}
