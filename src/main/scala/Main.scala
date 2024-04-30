@main def run: Unit =
  val input: String = ???
  val output: String = ???

def etl(inputFileName: String, outputFileName: String) = {
  val extracted = extract(inputFileName)
  val transformed = transform(extracted)
  load(transformed, outputFileName)
}

def extract(input: String): List[String] = ???
def transform(data: List[String]): List[String] = ???
def load(
    data: List[String],
    output: String = "src/main/resources/output.txt"
): Unit = ???
