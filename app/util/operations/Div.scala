package util.operation

import model._
import util.Key
import play.api.libs.json.{Json, Reads, Writes}
import play.api.libs.json.Reads.verifying
import util.operation.helper._

case class Div(name: String = Key.Div, mc: MColor) extends Operation {
  def myexec(e: ExecuteWrapper): MColor = {
    val c = e.c
    val newR = c.getRed() / mc.getRed()
    val newG = c.getGreen() / mc.getGreen()
    val newB = c.getBlue() / mc.getBlue()

    new MColor(newR, newG, newB, mc.getAlpha())
  }
}

object Div {
  val reads: Reads[Div] = verifying[Div](_.`name` == Key.Div)(Json.reads[Div])
  val writes: Writes[Div] = Json.writes[Div]
}