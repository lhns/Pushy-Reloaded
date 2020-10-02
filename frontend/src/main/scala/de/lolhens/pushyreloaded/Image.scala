package de.lolhens.pushyreloaded

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLImageElement

case class Image(src: String) {
  private var ready: Boolean = false

  val element: HTMLImageElement = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
  element.onload = (_: dom.Event) => ready = true
  element.src = src

  def isReady: Boolean = ready
}
