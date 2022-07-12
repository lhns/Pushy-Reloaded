package de.lolhens.pushyreloaded

import org.http4s.server.staticcontent.WebjarService.WebjarAsset
import scalatags.Text.TypedTag
import scalatags.Text.all._

object MainPage {
  def apply(webjarAsset: WebjarAsset): TypedTag[String] =
    html(style := "height: 100%;", lang := "de",
      head(
        meta(charset := "UTF-8"),
        base(href := "/"),
        tag("title")("Pushy Reloaded"),
        link(rel := "icon", `type` := "image/png", href := "assets/images/10.png", attr("sizes") := "32x32"),
      ),
      body(style := "height: 100%; margin: 0; touch-action: manipulation;",
        div(style := "height: 100%; display: flex; flex-direction: column; align-items: center; overflow: hidden;",
          div(style := "flex: 1"),
          div(style := "display: flex; flex-direction: row; overflow: hidden;",
            div(style := "flex: 1"),
            canvas(id := "canvas", style := "width: auto; height: auto; max-width: 100%; max-height: 100%;"),
            div(style := "flex: 1"),
          ),
          div(style := "display: flex; flex-direction: row;",
            input(id := "restart", `type` := "button", style := "height: 2em; width: 10em;", value := "Restart"),
            input(id := "shoot", `type` := "button", style := "height: 2em; width: 10em;", value := "Shoot"),
          ),
          div(style := "flex: 1"),
        ),
        script(src := Server.webjarUri(webjarAsset))
      )
    )
}
