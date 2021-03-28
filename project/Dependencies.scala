import sbt._

object Dependencies {
  
  lazy val grpc =
    "io.grpc" % "grpc-netty-shaded" % "1.36.1"
  lazy val scalapbruntime =
    "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion
  lazy val zio = "dev.zio" %% "zio" % "1.0.4"

}
