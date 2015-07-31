package samples

import akka.actor.{ActorLogging, Actor}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import samples.FooActor.Tick
import scala.concurrent.Future
import scala.concurrent.duration._

object FooActor {
  sealed trait InputMessage
  case object Tick extends InputMessage
}

class FooActor extends Actor with ActorLogging {

  import context.dispatcher
  implicit val flowMaterializer = ActorMaterializer()

  override def preStart(): Unit =
    context.system.scheduler.schedule(
      initialDelay = 1 second,
      interval = 1 second,
      receiver = self,
      message = Tick
    )

  override def receive: Receive = {
    case Tick =>
      val x = 1 to 20
      println(s"x is ${x.getClass.getName}")
      Source(x)
        .mapAsync(parallelism = 3) { i =>
          Future.successful(i * 2)
        }
        .to(Sink.foreach(i => log.info(s"Number is $i")))
        .run()
  }
}
