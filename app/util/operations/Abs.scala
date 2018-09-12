package util.operation

import model._
import util.Key
import play.api.libs.json.{Json, Reads, Writes}
import play.api.libs.json.Reads.verifying
import util.operation.helper._
import scala.math.abs

case class Abs(name: String = Key.Abs, mc: MColor) extends Operation {
  def myexec(e: ExecuteWrapper): MColor = {
    val c = e.c
    val newR = abs(c.getRed() - mc.getRed())
    val newG = abs(c.getGreen() - mc.getGreen())
    val newB = abs(c.getBlue() - mc.getBlue())

    new MColor(newR, newG, newB, mc.getAlpha())
  }
}

object Abs {
  val reads: Reads[Abs] = verifying[Abs](_.`name` == Key.Abs)(Json.reads[Abs])
  val writes: Writes[Abs] = Json.writes[Abs]
}