package samples

import akka.stream.scaladsl.{Sink, Source}
import org.scalatest.FunSpec
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Future

class SimpleStreamSpec extends FunSpec with TestBase with ScalaFutures {
  describe("simple stream") {
    describe("simple Source") {
      val source = Source(1 to 20)
      val multipleByTwo: Int => Int = _ * 2

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
  }
}
