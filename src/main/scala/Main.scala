import scala.util.Using
import scala.io.Source
import java.io.File
import java.io.FileWriter
@main def run: Unit =
  val input: String = ""
  val output: String = ""
  etl(input, output)

def etl(inputFileName: String, outputFileName: String) = {
  val extracted = extract(inputFileName)
  val transformed = transform(extracted)
  load(transformed, outputFileName)
}

def extract(input: String): List[String] =
  Using.resource(Source.fromFile(input))(_.getLines.toList)
def transform(data: List[String]): List[String] =
  data.map(_.toLowerCase)
def load(
    data: List[String],
    output: String = "src/main/resources/output.txt"
): Unit =
  val file = File(output)
  val fileWriter = FileWriter(file)
  fileWriter.write(data.mkString("\n"))
  fileWriter.close
