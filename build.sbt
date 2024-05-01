lazy val root = (project in file("."))
  .settings(
    name := "hands-on-scala",
    scalaVersion := "3.4.1",
    libraryDependencies ++= Seq(
      // For Scala 3 `%% "scalatest"` is the same as `% "scalatest_3"`
      "com.github.pureconfig" %% "pureconfig-core" % "0.17.6",
      "org.scalatest" %% "scalatest" % "3.2.18" % Test
    )
  )
