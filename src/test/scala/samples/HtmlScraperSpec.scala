package samples

import org.scalatest.{Matchers, FunSpec}
import samples.HtmlScraper.TitleTag

class HtmlScraperSpec extends FunSpec with Matchers {
  describe("scraper") {
    it("returns title tag given valid html") {
      val html =
        """
          |<html>
          | <head>
          |   <title>This is a title</title>
          | </head>
          |</html>
        """.stripMargin

      HtmlScraper.naiveScraper(html) shouldBe Some(TitleTag("This is a title"))
    }

    it("returns nothing given invalid html") {
      val html =
        """
          |<html>
          | <head>
          | </head>
          |</html>
        """.stripMargin

      HtmlScraper.naiveScraper(html) shouldBe None
    }
  }
}
