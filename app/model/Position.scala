package model

import play.api.libs.json.Json

case class Position(x: Int, y: Int) {}

object Position {
  implicit val reads = Json.reads[Position]
  implicit val writes = Json.writes[Position]
}