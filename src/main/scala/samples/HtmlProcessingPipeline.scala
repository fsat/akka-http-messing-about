package samples

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, Uri}
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import samples.HtmlScraper.{HtmlScraper, ScrapedContent}

import scala.concurrent.{ExecutionContext, Future}

object HtmlProcessingPipeline {
  import Unmarshaller._

  private val buildHttpRequest: Uri => HttpRequest = uri => HttpRequest(uri = uri)
  private def httpExchange(implicit actorSystem: ActorSystem, materializer: Materializer): HttpRequest => Future[HttpResponse] = Http().singleRequest(_)

  def htmlScraperPipeline[T <: ScrapedContent](scraper: HtmlScraper[T], parallelism: Int)
                                              (implicit actorSystem: ActorSystem,
                                               executionContext: ExecutionContext,
                                               materializer: Materializer): Source[Uri, Unit] => Source[Option[T], Unit] =
    _.map(buildHttpRequest)
      .mapAsyncUnordered(parallelism)(httpExchange)
      .mapAsyncUnordered(parallelism)(response => Unmarshal(response.entity).to[String])
      .mapAsyncUnordered(parallelism) { content => Future.successful(scraper(content)) }
}
