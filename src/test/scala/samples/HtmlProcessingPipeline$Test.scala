package samples

import akka.actor.ActorSystem
import akka.http.scaladsl.model.Uri
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import org.scalatest.{BeforeAndAfterAll, Matchers, FunSpec}
import HtmlProcessingPipeline.htmlScraperPipeline
import samples.HtmlScraper.{TitleTag, naiveScraper}
import org.scalatest.concurrent.ScalaFutures
import scala.concurrent.duration._

class HtmlProcessingPipeline$Test extends FunSpec with BeforeAndAfterAll with Matchers with ScalaFutures {
  implicit val actorSystem = ActorSystem()
  import actorSystem.dispatcher
  implicit val materializer = ActorMaterializer()

  override implicit def patienceConfig: PatienceConfig =
    PatienceConfig(timeout = 10 seconds, interval = 1 second)

  override protected def afterAll(): Unit =
    actorSystem.shutdown()

  it("scrapes title from a website") {
    val pipeline = htmlScraperPipeline(scraper = naiveScraper, parallelism = 1)

    val source = Source.single(Uri("http://www.example.com"))

    val result = pipeline(source).runWith(Sink.head)
    result.futureValue shouldBe Some(TitleTag("Example Domain"))
  }
}
