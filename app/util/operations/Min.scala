package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import util.operation.helper._
import scala.math.min

case class Min(name: String = Key.Min, mc: MColor) extends Arithmetic {
  def myexec(const: MColor)(e: ExecuteWrapper): ExecuteWrapper = {
    val c = e.c
    val newR = min(c.getRed(), const.getRed())
    val newG = min(c.getGreen(), const.getGreen())
    val newB = min(c.getBlue(), const.getBlue())

    ExecuteWrapper(e.rect, e.pos, e.img, new MColor(newR, newG, newB, c.getAlpha()))
  }
}

object Min {
  val reads: Reads[Min] = verifying[Min](_.`name` == Key.Min)(Json.reads[Min])
  val writes: Writes[Min] = Json.writes[Min]
}