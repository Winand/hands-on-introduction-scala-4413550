val Version = new {
  val scala = "3.4.1"
  val pureconfig = "0.17.6"
  val circe = "0.14.1"
  val scalatest = "3.2.18"
}

lazy val root = (project in file("."))
  .settings(
    name := "hands-on-scala",
    scalaVersion := Version.scala,
    libraryDependencies ++= Seq(
      "com.github.pureconfig" %% "pureconfig-core" % Version.pureconfig,
      // https://circe.github.io/circe/quickstart.html
      "io.circe" %% "circe-core" % Version.circe,
      "io.circe" %% "circe-generic" % Version.circe,
      "io.circe" %% "circe-parser" % Version.circe,
      // For Scala 3 `%% "scalatest"` is the same as `% "scalatest_3"`
      "org.scalatest" %% "scalatest" % Version.scalatest % Test
    )
  )
