package model

import java.awt.Color
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.{Json, Reads, Writes}
import play.api.libs.json.Reads.verifying

case class MColor (r: Float, g: Float, b: Float, a: Float) {
  def this(rgba: Int) = {
    this(((rgba & 0x00ff0000) >> 16).toFloat/255, ((rgba & 0x0000ff00) >> 8).toFloat/255, (rgba & 0x000000ff).toFloat/255, ((rgba >> 24) & 0x000000ff).toFloat/255)
  }

  def getRGBA(): Int = {
    println((a*255).toInt)
    println((r*255).toInt)
    println((g*255).toInt)
    println((b*255).toInt)
    println("\n")
    val col = ((a*255).toInt << 24) | ((r*255).toInt << 16) | ((g*255).toInt << 8) | ((b*255).toInt)
    println(col)
    col
  }

  def getAlpha(): Float = a
  def getRed(): Float = r
  def getGreen(): Float = g
  def getBlue(): Float = b
}

object MColor {
  implicit val reads = Json.reads[MColor]
  implicit val writes = Json.writes[MColor]
}