package samples

import scala.xml.{Elem, XML}

object HtmlScraper {
  case class TitleTag(title: String)

  private val parseDom: String => Elem = XML.loadString
  private val extractTitleTag: Elem => Option[TitleTag] =
    Option(_)
      .map(_ \ "head" \ "title")
      .map(_.text)
      .filterNot(_.isEmpty)
      .map(TitleTag)

  val naiveScraper: String => Option[TitleTag] =
    parseDom andThen
    extractTitleTag
}
