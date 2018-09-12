package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import util.operation.helper._

case class Sub(name: String = Key.Sub, mc: MColor) extends Operation {
  def myexec(e: ExecuteWrapper): MColor = {
    val c = e.c
    val newR = c.getRed() - mc.getRed()
    val newG = c.getGreen() - mc.getGreen()
    val newB = c.getBlue() - mc.getBlue()

    new MColor(newR, newG, newB, c.getAlpha())
  }
}

object Sub {
  val reads: Reads[Sub] = verifying[Sub](_.`name` == Key.Sub)(Json.reads[Sub])
  val writes: Writes[Sub] = Json.writes[Sub]
}