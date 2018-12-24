package util.operation

import model._
import util.Key
import play.api.libs.json.{Json, Reads, Writes}
import play.api.libs.json.Reads.verifying
import util.operation.helper._

case class Div(name: String = Key.Div, mc: MColor) extends Arithmetic {
  def myexec(const: MColor)(e: ExecuteWrapper): ExecuteWrapper = {
    val c = e.c
    val newR = c.getRed() / const.getRed()
    val newG = c.getGreen() / const.getGreen()
    val newB = c.getBlue() / const.getBlue()

    ExecuteWrapper(e.rect, e.pos, e.img, new MColor(newR, newG, newB, c.getAlpha()))
  }
}

object Div {
  val reads: Reads[Div] = verifying[Div](_.`name` == Key.Div)(Json.reads[Div])
  val writes: Writes[Div] = Json.writes[Div]
}