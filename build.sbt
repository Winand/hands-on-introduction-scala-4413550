lazy val root = (project in file("."))
  .settings(
    name := "hands-on-scala",
    scalaVersion := "3.4.1",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test"
  )
