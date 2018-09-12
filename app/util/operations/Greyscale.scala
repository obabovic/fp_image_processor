package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import util.operation.helper._

case class Greyscale(name: String = Key.Greyscale) extends Operation {
  def myexec(e: ExecuteWrapper): MColor = {
    val c = e.c
    val newR = (c.getRed() + c.getGreen() + c.getBlue())/3
    new MColor(newR, newR, newR, c.getAlpha())
  }
}

object Greyscale {
  val reads: Reads[Greyscale] = verifying[Greyscale](_.`name` == Key.Greyscale)(Json.reads[Greyscale])
  val writes: Writes[Greyscale] = Json.writes[Greyscale]
}