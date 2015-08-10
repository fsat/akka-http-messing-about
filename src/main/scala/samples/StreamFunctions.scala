package samples

import akka.stream.OverflowStrategy
import akka.stream.scaladsl.Source

object StreamFunctions {
  def enableBuffering[X,Y](size: Int, overflowStrategy: OverflowStrategy): Source[X,Y] => Source[X,Y] =
    _.buffer(size, overflowStrategy)
}
