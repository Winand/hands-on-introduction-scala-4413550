object Etl {
  // "sealed" can be extended in the same file only
  sealed trait Etl[A, B]:
    def extract(input: String): A
    def transform(data: A): B
    def load(data: B, output: String): Unit

  given StringImpl: Etl[List[String], List[String]] with
    def extract(input: String): List[String] = FileUtils.extract(input)
    def transform(data: List[String]): List[String] = data.map(_.toLowerCase)
    def load(data: List[String], output: String): Unit =
      FileUtils.load(data, output)

  given IntImpl: Etl[List[String], List[Int]] with
    def extract(input: String): List[String] = FileUtils.extract(input)
    def transform(data: List[String]): List[Int] = data.map(_.toInt * 2)
    def load(data: List[Int], output: String): Unit =
      FileUtils.load(data, output)

  def etl[A, B](inputFileName: String, outputFileName: String)(using
      etlImpl: Etl[A, B]
  ): Unit = {
    val extracted = etlImpl.extract(inputFileName)
    val transformed = etlImpl.transform(extracted)
    etlImpl.load(transformed, outputFileName)
  }
}
