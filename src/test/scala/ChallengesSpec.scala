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
      val testVals3 = List()
      val expected3 = 0
      val testVals4 = 0 to 99
      val expected4 = testVals4.length * (testVals4.start + testVals4.end) / 2

      calculateSum(testVals1) shouldEqual expected1
      calculateSum(testVals2) shouldEqual expected2
      calculateSum(testVals3) shouldEqual expected3
      calculateSum(testVals4.toList) shouldEqual expected4
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
