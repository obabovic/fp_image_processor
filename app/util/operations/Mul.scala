package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import util.operation.helper._

case class Mul(name: String = Key.Mul, mc: MColor) extends Operation {
  def myexec(e: ExecuteWrapper): MColor = {
    val c = e.c
    val newR = c.getRed() * mc.getRed()
    val newG = c.getGreen() * mc.getGreen()
    val newB = c.getBlue() * mc.getBlue()

    new MColor(newR, newG, newB, mc.getAlpha())
  }
}

object Mul {
  val reads: Reads[Mul] = verifying[Mul](_.`name` == Key.Mul)(Json.reads[Mul])
  val writes: Writes[Mul] = Json.writes[Mul]
}