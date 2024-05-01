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

  "applyFunction" - {
    "applies a function to an integer" in {
      val square: Int => Int = num => num * num
      val result = applyFunction(square, 5)
      result shouldBe 25
    }

    "applies a function to a string" in {
      val greet: String => String = name => s"Hello, $name!"
      val result = applyFunction(greet, "Akin")
      result shouldBe "Hello, Akin!"
    }

    "applies a function to a case class" in {
      case class Person(name: String, age: Int)
      val getDescription: Person => String =
        person => s"${person.name} is ${person.age} years old."
      val person = Person("Coco", 65)
      val result = applyFunction(getDescription, person)
      result shouldBe "Coco is 65 years old."
    }
  }

  "processPayment" - {
    "processes successful payment" in {
      val amount = 50.0
      val cardBalance = 100.0
      val result = processPayment(amount, cardBalance)
      result shouldBe Right(50.0)
    }

    "handles insufficient balance" in {
      val amount = 150.0
      val cardBalance = 100.0
      val result = processPayment(amount, cardBalance)
      result shouldBe Left("Insufficient balance")
    }
  }
  "getWeatherDescription" - {
    "should describe the weather conditions correctly" - {
      "for Sunny weather" in {
        val description = getWeatherDescription(WeatherCondition.Sunny)
        description shouldBe "It's a sunny day."
      }

      "for Cloudy weather" in {
        val description = getWeatherDescription(WeatherCondition.Cloudy)
        description shouldBe "It's a cloudy day."
      }

      "for Rainy weather" in {
        val description = getWeatherDescription(WeatherCondition.Rainy)
        description shouldBe "It's a rainy day."
      }

      "for Snowy weather" in {
        val description = getWeatherDescription(WeatherCondition.Snowy)
        description shouldBe "It's a snowy day."
      }
    }
  }

  "Notifications" - {
    "EmailNotification" in {
      val emailAddress = "user@example.com"
      val emailNotification = EmailNotification(emailAddress, Priority.Medium)
      val message = "Hello, this is an email"
      val formattedMessage = emailNotification.formatMessage(message)
      val result = emailNotification.sendNotification(message)

      formattedMessage shouldBe s"Message: $message"
      result shouldBe s"Sending email to $emailAddress with message: $message"
    }
    "SMSNotification" in {
      val phoneNumber = "07525015566"
      val smsNotification = SMSNotification(phoneNumber, Priority.High)
      val message = "Hello, this is an SMS"
      val formattedMessage = smsNotification.formatMessage(message)
      val result = smsNotification.sendNotification(message)

      formattedMessage shouldBe s"Message: $message"
      result shouldBe s"Sending SMS to $phoneNumber: $message"
    }
  }
}
