package de.lolhens.pushyreloaded

import buildinfo.BuildInfo
import org.http4s.server.staticcontent.WebjarService.WebjarAsset
import scalatags.Text.TypedTag
import scalatags.Text.all._

object MainPage {
  private lazy val frontendWebjarAsset: WebjarAsset = WebjarAsset(
    BuildInfo.frontendName,
    BuildInfo.frontendVersion,
    BuildInfo.frontendAsset
  )

  def apply(): TypedTag[String] =
    html(style := "height: 100%;", lang := "de",
      head(
        meta(charset := "UTF-8"),
        tag("title")("Pushy Reloaded"),
        link(rel := "icon", `type` := "image/png", href := "/assets/images/10.png", attr("sizes") := "32x32"),
      ),
      body(style := "height: 100%; margin: 0;",
        div(style := "height: 100%; display: flex; flex-direction: column; overflow: hidden;",
          div(style := "flex: 1"),
          div(style := "display: flex; flex-direction: row; overflow: hidden;",
            div(style := "flex: 1"),
            canvas(id := "canvas", style := "width: auto; height: auto; max-width: 100%; max-height: 100%;"),
            div(style := "flex: 1"),
          ),
          div(style := "flex: 1"),
        ),
        script(src := Server.webjarUri(frontendWebjarAsset))
      )
    )
}
