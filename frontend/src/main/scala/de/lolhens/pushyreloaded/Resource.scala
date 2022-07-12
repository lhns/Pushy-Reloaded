package de.lolhens.pushyreloaded

import cats.effect.IO
import org.scalajs.dom

import java.io.ByteArrayInputStream
import scala.scalajs.js.typedarray.{ArrayBuffer, Uint8Array}

object Resource {
  private val assetRoot = "assets"

  def image(name: String): Image =
    Image(s"$assetRoot/images/$name")

  def request(url: String): IO[Array[Byte]] =
    IO.async_[Array[Byte]] { callback =>
      val xhr = new dom.XMLHttpRequest()
      xhr.open("GET", url)
      xhr.responseType = "arraybuffer"
      xhr.onload = { (_: dom.Event) =>
        if (xhr.status == 200) {
          val uint8Array = new Uint8Array(xhr.response.asInstanceOf[ArrayBuffer])
          val array = new Array[Byte](uint8Array.byteLength)
          for (i <- array.indices)
            array(i) = uint8Array(i).toByte
          callback(Right(array))
        } else {
          callback(Left(new RuntimeException(s"xml http request failed: ${xhr.status}")))
        }
      }
      xhr.send()
    }

  def level(name: String): IO[World] =
    request(s"$assetRoot/level/$name").map { bytes =>
      val inputStream = new ByteArrayInputStream(bytes)
      PushyLevelFormat.loadFromInputStream(inputStream)
    }
}
