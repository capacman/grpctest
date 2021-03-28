import Dependencies._

ThisBuild / scalaVersion := "2.13.4"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val scalacopts = Seq(
  "-feature",
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-language:postfixOps",
  "-language:higherKinds",
  "-Wunused:imports",
  "-Ymacro-annotations"
)

lazy val root = (project in file("."))
  .settings(
    name := "grpctest",
    libraryDependencies ++= Seq(
      grpc,
      scalapbruntime,
      zio
    ),
    PB.targets in Compile := Seq(
      scalapb
        .gen(grpc = true) -> (sourceManaged in Compile).value / "scalapb",
      scalapb.zio_grpc.ZioCodeGenerator -> (sourceManaged in Compile).value / "scalapb"
    ),
    scalacOptions ++= scalacopts
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
