package util.operation

import model._
import util.Key
import play.api.libs.json.{Json, Reads, Writes}
import play.api.libs.json.Reads.verifying
import util.operation.helper._

case class Add(name: String = Key.Add, mc: MColor) extends Arithmetic {
  def myexec(const: MColor)(e: ExecuteWrapper): ExecuteWrapper = {
    val c = e.c
    val newR = c.getRed() + const.getRed()
    val newG = c.getGreen() + const.getGreen()
    val newB = c.getBlue() + const.getBlue()
    ExecuteWrapper(e.rect, e.pos, e.img, new MColor(newR, newG, newB, c.getAlpha()))
  }
}

object Add {
  val reads: Reads[Add] = verifying[Add](_.`name` == Key.Add)(Json.reads[Add])
  val writes: Writes[Add] = Json.writes[Add]
}