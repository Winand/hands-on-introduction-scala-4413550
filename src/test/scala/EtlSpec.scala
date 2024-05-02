// FreeSpec test style https://www.scalatest.org/user_guide/selecting_a_style
import org.scalatest.freespec.AnyFreeSpec
// Matchers - DSL for asserts https://www.scalatest.org/user_guide/using_matchers
import org.scalatest.matchers.should.Matchers
import scala.util.Try
import scala.io.Source
import scala.util.Using
import scala.util.Success
import Etl.{etl, Etl, EtlError}
import Etl.EtlError._
import pureconfig.ConfigSource
import org.scalatest.BeforeAndAfterAll
import java.io.File
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.Path

class EtlSpec extends AnyFreeSpec with Matchers with BeforeAndAfterAll {
  val stringInput = "src/test/resources/input.txt"
  val intInput = "src/test/resources/input2.txt"
  val jsonInput = "src/test/resources/input3.txt"
  val stringOutput = "src/test/resources/output.txt"
  val intOutput = "src/test/resources/output2.txt"
  val jsonOutput = "src/test/resources/output3.txt"

  val inputJson: String = """
    |[
    |{"id": 1, "name": "bola", "age": 65},
    |{"id": 2, "name": "mary", "age": 15}
    |]
    """.stripMargin

  override def beforeAll() =
    writeTestInputFile(stringInput, "HELLO WORLD")
    writeTestInputFile(intInput, "0\n1\n2\n3\n4\n5\n")
    writeTestInputFile(jsonInput, inputJson)

  override def afterAll() =
    // File(...).delete doesn't throw error
    List(
      stringInput,
      intInput,
      jsonInput,
      stringOutput,
      intOutput,
      jsonOutput
    ).foreach(filePath => Files.delete(Path.of(filePath)))

  "etl" - {
    "transforms a text file by making all the text lowercase and saving this to a new file" in {
      val expected = List("hello world")
      runIntegratedTest("string-test", Etl.StringImpl, expected)
    }

    "transforms a text file by doubling all integers and saves this to a new file" in {
      val expected = List("0", "2", "4", "6", "8", "10")
      runIntegratedTest("int-test", Etl.IntImpl, expected)
    }

    "transforms a json file by removing all users under the age of 18" in {
      val expected = List("User(1,bola,65)")
      runIntegratedTest("json-test", Etl.JsonImpl, expected)
    }

    "outputs an extract error if the input file path does not exist" in {
      val configWithErroneousFilePath = """
        |input-file-path = "src/test/resources/not-found.txt"
        |output-file-path = "src/test/resources/output.txt"
        |etl-impl = string-impl
        |""".stripMargin
      isExpectedError(configWithErroneousFilePath, Etl.StringImpl, ExtractError)
    }

    "outputs an load error if the output file path does not exist" in {
      val configWithErroneousFilePath = """
        |input-file-path = "src/test/resources/input2.txt"
        |output-file-path = ""
        |etl-impl = int-impl
        |""".stripMargin
      isExpectedError(configWithErroneousFilePath, Etl.IntImpl, LoadError)
    }
  }

  private def readFile(filePath: String): Try[List[String]] =
    Using(Source.fromFile(filePath))(_.getLines.toList)

  private def runIntegratedTest[A, B, C](
      configPath: String,
      etlImpl: Etl[A, B],
      expected: C
  ) =
    // `at` - load a specific config section
    ConfigSource.default.at(configPath).load[EtlConfig] match
      case Left(e) => fail(s"Failed to load test config: $e")
      case Right(config) =>
        etl(config, etlImpl)
        readFile(config.outputFilePath) shouldEqual Success(expected)

  private def isExpectedError[A, B](
      config: String,
      etlImpl: Etl[A, B],
      error: EtlError
  ) = ConfigSource.string(config).load[EtlConfig] match
    case Left(e)       => fail(s"Failed to load test config: $e")
    case Right(config) => etl(config, etlImpl) shouldEqual Left(error)

  private def writeTestInputFile(path: String, contents: String) =
    val writer = FileWriter(File(path))
    try
      writer.write(contents)
    finally
      writer.close
}
