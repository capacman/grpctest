package example

import test_service.test.ZioTest
import zio.stream._
import zio._
import test_service.test.BinaryData
import com.google.protobuf.ByteString
import zio.duration._
import zio.console._
import scalapb.zio_grpc._
import io.grpc.ManagedChannelBuilder
object TestClient extends App {

  override def run(args: List[String]): URIO[ZEnv, ExitCode] = program.provideSomeLayer[ZEnv](clientLayer)

  def stream10mb = Stream
    .fromIterable(0 until 130)
    .map { _ =>
      Array.fill[Byte](1024 * 1024)(0)
    }
    .map { data =>
      BinaryData(data = ByteString.copyFrom(data))
    }

    val clientLayer = ZioTest.TestLoaderServiceClient.live(
      ZManagedChannel(
        ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext()
      )
    ).orDie

  def load10mb = ZioTest.TestLoaderServiceClient.load(stream10mb)

  val program = Stream
    .repeatEffect(load10mb.delay(100 millisecond))
    .mapM(a => putStrLn(a.toString()))
    .runDrain
    .mapError(s => new RuntimeException(s.toString()))
    .orDie
    .map(_=>ExitCode.success)

    
}
