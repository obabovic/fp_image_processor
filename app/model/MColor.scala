package model

import java.awt.Color
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.{Json, Reads, Writes}
import play.api.libs.json.Reads.verifying
import scala.math.{min, max}

case class MColor (r: Float, g: Float, b: Float, a: Float) {
  def this(rgba: Int) = {
    this(((rgba & 0x00ff0000) >> 16).toFloat/255, ((rgba & 0x0000ff00) >> 8).toFloat/255, (rgba & 0x000000ff).toFloat/255, ((rgba >> 24) & 0x000000ff).toFloat/255)
  }

  def getRGBA(): Int = {
    ((a*255).toInt << 24) | ((r*255).toInt << 16) | ((g*255).toInt << 8) | ((b*255).toInt)
  }

  def getAlpha(): Float = min(max(a, 0.0).toFloat, 1.0f)
  def getRed(): Float = min(max(r, 0.0).toFloat, 1.0f)
  def getGreen(): Float = min(max(g, 0.0).toFloat, 1.0f)
  def getBlue(): Float = min(max(b, 0.0).toFloat, 1.0f)
}

object MColor {
  implicit val reads = Json.reads[MColor]
  implicit val writes = Json.writes[MColor]
}