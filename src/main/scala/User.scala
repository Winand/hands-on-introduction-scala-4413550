/*
  https://circe.github.io/circe/codecs/semiauto-derivation.html
 */

import io.circe._, io.circe.generic.semiauto._, io.circe.syntax._

final case class User(id: Int, name: String, age: Int)

implicit val userDecoder: Decoder[User] = deriveDecoder
