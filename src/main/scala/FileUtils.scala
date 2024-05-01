import scala.util.Using
import scala.io.Source
import java.io.File
import java.io.FileWriter

object FileUtils {
  def extract(input: String): List[String] =
    Using.resource(Source.fromFile(input))(_.getLines.toList)

  def load[A](
      data: List[A],
      output: String = "src/main/resources/output.txt"
  ): Unit =
    val file = File(output)
    val fileWriter = FileWriter(file)
    fileWriter.write(data.mkString("\n"))
    fileWriter.close
}
