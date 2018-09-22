package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import util.operation.helper._

case class Mul(name: String = Key.Mul, mc: MColor) extends Arithmetic {
  def myexec(const: MColor)(e: ExecuteWrapper): ExecuteWrapper = {
    val c = e.c
    val newR = c.getRed() * const.r
    val newG = c.getGreen() * const.g
    val newB = c.getBlue() * const.b

    ExecuteWrapper(e.rect, e.pos, e.img, new MColor(newR, newG, newB, c.getAlpha()))
  }
}

object Mul {
  val reads: Reads[Mul] = verifying[Mul](_.`name` == Key.Mul)(Json.reads[Mul])
  val writes: Writes[Mul] = Json.writes[Mul]
}