package example

import test_service.test.{BinaryData, TestResponse}
import test_service.test.ZioTest
import io.grpc.Status
import zio._
import zio.stream._

object TestHeadService extends ZioTest.TestLoaderService {

  override def load(
      request: Stream[Status, BinaryData]
  ): IO[Status, TestResponse] = for {
    _ <- ZIO.unit
    _ = println("started")
    firstElementO <- request.runHead
    _ = println(s"take head opt with is some ${firstElementO.isDefined}")
    fe <- ZIO.fromOption(firstElementO).mapError(_ => Status.DATA_LOSS)
    _ = println("took for option")
    _ = println(s"size of first element limit ${fe.data.asReadOnlyByteBuffer().limit()} capacity ${fe.data.asReadOnlyByteBuffer().capacity()}")
  } yield TestResponse(success = true)
}
