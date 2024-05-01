// FreeSpec test style https://www.scalatest.org/user_guide/selecting_a_style
import org.scalatest.freespec.AnyFreeSpec
// Matchers - DSL for asserts https://www.scalatest.org/user_guide/using_matchers
import org.scalatest.matchers.should.Matchers
import scala.util.Try
import scala.io.Source
import scala.util.Using
import scala.util.Success
import Etl.etl
import Etl.EtlError._

class EtlSpec extends AnyFreeSpec with Matchers {
  "etl" - {
    "transforms a text file by making all the text lowercase and saving this to a new file" in {
      val input: String = "src/test/scala/resources/input.txt"
      val output: String = "src/test/scala/resources/output.txt"
      val expectedFileContents = List("hello world")
      etl(input, output)(using Etl.StringImpl)
      readFile(output) shouldEqual Success(expectedFileContents)
    }

    "transforms a text file by doubling all integers and saves this to a new file" in {
      val input: String = "src/test/scala/resources/input2.txt"
      val output: String = "src/test/scala/resources/output2.txt"
      val expectedFileContents = List("0", "2", "4", "6", "8", "10")
      etl(input, output)(using Etl.IntImpl)
      readFile(output) shouldEqual Success(expectedFileContents)
    }

    "outputs an extract error if the input file path does not exist" in {
      val erroneousInput: String = "src/test/scala/resources/not-found.txt"
      val output: String = "src/test/scala/resources/output.txt"
      etl(erroneousInput, output)(using Etl.IntImpl) shouldEqual Left(
        ExtractError
      )
    }

    "outputs an load error if the output file path does not exist" in {
      val input: String = "src/test/scala/resources/input2.txt"
      val erroneousOutput: String = ""
      etl(input, erroneousOutput)(using Etl.IntImpl) shouldEqual Left(
        LoadError
      )
    }
  }

  private def readFile(filePath: String): Try[List[String]] =
    Using(Source.fromFile(filePath))(_.getLines.toList)
}
