import java.io.File
import java.io.FileWriter
import scala.io.Source
import scala.util.Using
import Etl.{etl, Etl}
import pureconfig.ConfigSource

@main def run: Unit =
  /*ConfigSource.default
    .load[EtlConfig] // Left - error, Right - EtlConfig
    .flatMap(_.etlImpl match // if Right
      case EtlImpl.StringImpl => ???
      case EtlImpl.IntImpl    => ???
    )*/

  // same as flatMap: yields a value if Either is Right
  for config <- ConfigSource.default.load[EtlConfig]
  yield config.etlImpl match
    case EtlImpl.StringImpl => printResult(config, Etl.StringImpl)
    case EtlImpl.IntImpl    => printResult(config, Etl.IntImpl)

private def printResult[A, B](config: EtlConfig, etlImpl: Etl[A, B]): Unit =
  etl(config, etlImpl) match
    case Left(error) => println(s"Failure: $error")
    case Right(_)    => println("Success!")
