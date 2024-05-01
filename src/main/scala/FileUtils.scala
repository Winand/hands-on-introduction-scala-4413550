import scala.util.Using
import scala.io.Source
import java.io.File
import java.io.FileWriter
import Etl.EtlError
import Etl.EtlError._
import scala.util.Try

object FileUtils {
  def extract(input: String): Either[EtlError, List[String]] =
    try Right(Using.resource(Source.fromFile(input))(_.getLines.toList))
    catch case e: Exception => Left(ExtractError)

  def load[A](
      data: List[A],
      output: String = "src/main/resources/output.txt"
  ): Either[EtlError, Unit] =
    Try {
      val file = File(output)
      val fileWriter = FileWriter(file)
      fileWriter.write(data.mkString("\n"))
      fileWriter.close
    }.toEither.left.map(_ => LoadError)
    //   Right(())
    // } catch case e: Exception => Left(LoadError)
}
