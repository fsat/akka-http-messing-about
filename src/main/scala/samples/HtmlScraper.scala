package samples

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object HtmlScraper {
  trait ScrapedContent

  type HtmlScraper[T <: ScrapedContent] = String => Option[T]

  case class TitleTag(title: String) extends ScrapedContent

  private val parseDom: String => Document = Jsoup.parse
  private val extractTitleTag: Document => Option[TitleTag] = input =>
    Option(input.select("title").first())
      .map(_.text())
      .map(TitleTag)

  val naiveScraper: HtmlScraper[TitleTag] =
    parseDom andThen
    extractTitleTag
}
