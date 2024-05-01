import pureconfig.ConfigReader
import pureconfig.generic.derivation.default.derived
import pureconfig.generic.derivation.EnumConfigReader

enum EtlImpl derives EnumConfigReader:
  case StringImpl, IntImpl

final case class EtlConfig(
    inputFilePath: String,
    outputFilePath: String,
    etlImpl: EtlImpl
) derives ConfigReader
