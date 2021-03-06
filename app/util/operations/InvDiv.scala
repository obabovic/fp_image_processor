package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import util.operation.helper._

case class InvDiv(name: String = Key.Invdiv, mc: MColor) extends Arithmetic {
  def myexec(const: MColor)(e: ExecuteWrapper): ExecuteWrapper = {
    val c = e.c
    val newR = const.getRed() / c.getRed()
    val newG = const.getGreen() / c.getGreen()
    val newB = const.getBlue() / c.getBlue()

    ExecuteWrapper(e.rect, e.pos, e.img, new MColor(newR, newG, newB, c.getAlpha()))
  }
}

object InvDiv {
  val reads: Reads[InvDiv] = verifying[InvDiv](_.`name` == Key.Invdiv)(Json.reads[InvDiv])
  val writes: Writes[InvDiv] = Json.writes[InvDiv]
}