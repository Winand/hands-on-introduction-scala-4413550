import io.circe.parser.decode

object Etl {
  enum EtlError:
    case ExtractError, LoadError, JsonParseError

  // "sealed" can be extended in the same file only
  sealed trait Etl[A, B]:
    def extract(input: String): Either[EtlError, A]
    def transform(data: A): Either[EtlError, B]
    def load(data: B, output: String): Either[EtlError, Unit]

  given StringImpl: Etl[List[String], List[String]] with
    def extract(input: String): Either[Etl.EtlError, List[String]] =
      FileUtils.extract(input)
    def transform(data: List[String]): Either[Etl.EtlError, List[String]] =
      Right(data.map(_.toLowerCase)).withLeft[EtlError]
    def load(data: List[String], output: String): Either[Etl.EtlError, Unit] =
      FileUtils.load(data, output)

  given IntImpl: Etl[List[String], List[Int]] with
    def extract(input: String): Either[Etl.EtlError, List[String]] =
      FileUtils.extract(input)
    def transform(data: List[String]): Either[Etl.EtlError, List[Int]] =
      Right(data.map(_.toInt * 2)).withLeft[EtlError]
    def load(data: List[Int], output: String): Either[Etl.EtlError, Unit] =
      FileUtils.load(data, output)

  given JsonImpl: Etl[List[String], List[User]] with
    def extract(input: String): Either[Etl.EtlError, List[String]] =
      FileUtils.extract(input)
    def transform(data: List[String]): Either[EtlError, List[User]] =
      // .left.map(...) - if Left then change to EtlError
      for {
        users <- decode[List[User]](data.mkString).left.map(_ =>
          EtlError.JsonParseError
        )
        usersOver18 = users.filter(_.age >= 18)
      } yield usersOver18
    def load(data: List[User], output: String): Either[Etl.EtlError, Unit] =
      FileUtils.load(data, output)

  def etl[A, B](
      config: EtlConfig,
      etlImpl: Etl[A, B]
  ): Either[EtlError, Unit] = {
    // If any operation inside for-expression results in a Left (EtlError),
    // this first Left is returned from the function.
    // Otherwise Right is being processed and Unit is returned.
    for
      extracted <- etlImpl.extract(config.inputFilePath)
      transformed <- etlImpl.transform(extracted)
      _ <- etlImpl.load(transformed, config.outputFilePath)
    yield ()
  }
}
