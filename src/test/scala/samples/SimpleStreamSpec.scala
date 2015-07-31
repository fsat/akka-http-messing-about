package samples

import akka.stream.scaladsl.{RunnableGraph, Keep, Sink, Source}
import org.scalatest.FunSpec
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Future

class SimpleStreamSpec extends FunSpec with TestBase with ScalaFutures {
  val source = Source(1 to 20)
  val multipleByTwo: Int => Int = _ * 2

  describe("simple stream from simple Source") {
    describe("from simple Source") {
      it("can be mapped over and poured into a Sink") {
        val result = source
          .map(multipleByTwo)
          .runWith(Sink.fold(0) { _ + _})

        result.futureValue shouldBe 420
      }

      it("can be mapped over asynchronously and poured into a Sink") {
        val result = source
          .mapAsync(parallelism = 3) { value =>
            Future.successful(multipleByTwo(value))
          }
          .runWith(Sink.fold(0) { _ + _})

        result.futureValue shouldBe 420
      }
    }

    it("can be setup as a pre-canned processing pipeline") {
      val processingPipeline: Source[Int, Unit] => RunnableGraph[Future[Int]] =
        _.mapAsync(parallelism = 3) { value =>
          Future.successful(multipleByTwo(value))
        }
        .toMat(Sink.fold(0) { _ + _})(Keep.right)

      processingPipeline(source).run().futureValue shouldBe 420
    }
  }
}
