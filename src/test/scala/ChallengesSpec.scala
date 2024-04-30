import org.scalatest._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import Challenges._

class ChallengesSpec extends AnyFreeSpec with Matchers {
  "calculateSum" - {
    "should calculate the sum of a list of integers" in {
      val testVals1 = List(1, 2, 3, 4)
      val expected1 = 10
      val testVals2 = List(0)
      val expected2 = 0
      val testVals3 = 0 to 99
      val expected3 = testVals3.length * (testVals3.start + testVals3.end) / 2

      calculateSum(testVals1) shouldEqual expected1
      calculateSum(testVals2) shouldEqual expected2
      calculateSum(testVals3.toList) shouldEqual expected3
    }

    "should return 0 for an empty list" in {
      // https://stackoverflow.com/questions/5981850/scala-nil-vs-list
      calculateSum(Nil) shouldEqual 0
    }
  }

  "filterAndConvert" - {
    "filters names with less than four characters and converts them to uppercase" in {
      val inputNames = List("Ravi", "Akiko", "Satoshi", "Priya", "Juan", "Bola")
      val expectedOutput = List("RAVI", "JUAN", "BOLA")
      val result = filterAndConvert(inputNames)
      result shouldBe expectedOutput
    }

    "returns an empty list when there are no names with less than four characters" in {
      val inputNames = List("Michael", "Sophia", "William")
      val result = filterAndConvert(inputNames)
      result shouldBe empty
    }
  }
}
