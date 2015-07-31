package samples

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, Suite, BeforeAndAfterAll}

trait TestBase extends BeforeAndAfterAll with Matchers with ScalaFutures {
  this: Suite =>

  implicit val actorSystem = ActorSystem()
  implicit val flowMaterializer = ActorMaterializer.create(actorSystem)

  override protected def afterAll(): Unit =
    actorSystem.shutdown()
}
