import java.io.File
import java.io.FileWriter
import scala.io.Source
import scala.util.Using
import Etl.{etl, given}

@main def run: Unit =
  val input: String = "src/main/scala/resources/input.txt"
  val output: String = "src/main/scala/resources/output.txt"
  etl(input, output)(using IntImpl)
