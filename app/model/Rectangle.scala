package model

import play.api.libs.json.Json

case class Rectangle(start: Position, end: Position) {}

object Rectangle {
  implicit val reads = Json.reads[Rectangle]
  implicit val writes = Json.writes[Rectangle]
}