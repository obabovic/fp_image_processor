package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import util.operation.helper._
import scala.math.pow

case class Pow(name: String = Key.Pow, mc: MColor) extends Arithmetic {
  def myexec(const: MColor)(e: ExecuteWrapper): ExecuteWrapper = {
    val c = e.c
    val newR = pow(c.getRed(), const.getRed())
    val newG = pow(c.getGreen(), const.getGreen())
    val newB = pow(c.getBlue(), const.getBlue())

    ExecuteWrapper(e.rect, e.pos, e.img, new MColor(newR.toFloat, newG.toFloat, newB.toFloat, c.getAlpha()))
  }
}

object Pow {
  val reads: Reads[Pow] = verifying[Pow](_.`name` == Key.Pow)(Json.reads[Pow])
  val writes: Writes[Pow] = Json.writes[Pow]
}