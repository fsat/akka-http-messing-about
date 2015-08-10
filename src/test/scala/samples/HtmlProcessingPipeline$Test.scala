package samples

import akka.actor.ActorSystem
import akka.http.scaladsl.model.Uri
import akka.stream.{OverflowStrategy, ActorMaterializer}
import akka.stream.scaladsl.{Sink, Source}
import org.scalatest.{BeforeAndAfterAll, Matchers, FunSpec}
import HtmlProcessingPipeline.htmlScraperPipeline
import samples.HtmlScraper.{TitleTag, naiveScraper}
import org.scalatest.concurrent.ScalaFutures
import scala.concurrent.duration._
import StreamFunctions._

class HtmlProcessingPipeline$Test extends FunSpec with BeforeAndAfterAll with Matchers with ScalaFutures {
  implicit val actorSystem = ActorSystem()
  import actorSystem.dispatcher
  implicit val materializer = ActorMaterializer()

  override implicit def patienceConfig: PatienceConfig =
    PatienceConfig(timeout = 10 seconds, interval = 1 second)

  override protected def afterAll(): Unit =
    actorSystem.shutdown()

  val source = Source.single(Uri("http://www.example.com"))
  val pipeline = htmlScraperPipeline(scraper = naiveScraper, parallelism = 1)

  it("scrapes title from a website") {
    val result = pipeline(source).runWith(Sink.head)
    result.futureValue shouldBe Some(TitleTag("Example Domain"))
  }

  describe("with buffering") {
    val pipelineWithBuffering = pipeline andThen enableBuffering(size = 10, OverflowStrategy.backpressure)

    it("scrapes title from a website") {
      val result = pipelineWithBuffering(source).runWith(Sink.head)
      result.futureValue shouldBe Some(TitleTag("Example Domain"))
    }
  }
}
