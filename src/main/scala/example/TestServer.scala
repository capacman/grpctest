package example
import zio._
import scalapb.zio_grpc.ServerLayer
import io.grpc.protobuf.services.ProtoReflectionService
import io.grpc.ServerBuilder
import zio.console._
object TestServer extends App {

  val port = 9000
  val serverLayer = ServerLayer.fromServiceLayer(
    ServerBuilder
      .forPort(port)
      .maxInboundMessageSize(24 * 1024 * 1024)
      .addService(ProtoReflectionService.newInstance())
  )(TestHeadService.toLayer)

  val myAppLogic = putStrLn("Server is running. Press Ctrl-C to stop.") *> serverLayer.build.useForever

  def run(args: List[String]) = myAppLogic.exitCode
}
