# Messing around with Akka HTTP and Akka Streams

A small, trivial project for playing around with Akka HTTP and Akka Streams.

Once checked out, the first thing to do is to make sure everything works.

```
sbt test
```

The test `samples.SimpleStreamSpec` illustrates the basic simple operations that can be applied to a stream.

The test `samples.HtmlProcessingPipeline$Test` provides a more tangible example:

* Sets up HTML processing pipeline using an instance of `samples.HtmlScraper`.
* Creates a single `Source` from a particular URI.
* Push the generated `Source` through the processing pipeline, collecting the result using `Sink`.

The class `samples.HtmlProcessingPipeline` has the main Akka HTTP and Akka Stream setup. This is where the most
interesting thing in this project takes place.  
