package util.operation

import model._
import util.Key
import play.api.libs.json.{Json, Reads, Writes}
import play.api.libs.json.Reads.verifying
import util.operation.helper._
import scala.math.abs

case class Abs(name: String = Key.Abs, mc: MColor) extends Arithmetic {
  def myexec(const: MColor)(e: ExecuteWrapper): ExecuteWrapper = {
    val c = e.c
    val newR = abs(c.getRed() - const.getRed())
    val newG = abs(c.getGreen() - const.getGreen())
    val newB = abs(c.getBlue() - const.getBlue())

    ExecuteWrapper(e.rect, e.pos, e.img, new MColor(newR, newG, newB, c.getAlpha()))
  }
}

object Abs {
  val reads: Reads[Abs] = verifying[Abs](_.`name` == Key.Abs)(Json.reads[Abs])
  val writes: Writes[Abs] = Json.writes[Abs]
}