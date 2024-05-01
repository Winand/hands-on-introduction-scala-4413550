import java.io.File
import java.io.FileWriter
import scala.io.Source
import scala.util.Using
import Etl.{etl, IntImpl}

@main def run: Unit =
  val input: String = "src/main/scala/resources/input.txt"
  val output: String = "src/main/scala/resources/output.txt"
  etl(input, output) match
    case Left(error) => println(s"Failure: $error")
    case Right(_)    => println("Success!")
