package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import util.operation.helper._
import scala.math.min

case class Min(name: String = Key.Min, mc: MColor) extends Operation {
  def myexec(e: ExecuteWrapper): MColor = {
    val c = e.c
    val newR = min(c.getRed(), mc.getRed())
    val newG = min(c.getGreen(), mc.getGreen())
    val newB = min(c.getBlue(), mc.getBlue())

    new MColor(newR, newG, newB, mc.getAlpha())
  }
}

object Min {
  val reads: Reads[Min] = verifying[Min](_.`name` == Key.Min)(Json.reads[Min])
  val writes: Writes[Min] = Json.writes[Min]
}