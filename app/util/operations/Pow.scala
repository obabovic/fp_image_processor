package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import util.operation.helper._
import scala.math.pow

case class Pow(name: String = Key.Pow, r: Float, g: Float, b: Float) extends Operation {
  def myexec(e: ExecuteWrapper): MColor = {
    val c = e.c
    val newR = pow(c.getRed(), r)
    val newG = pow(c.getGreen(), g)
    val newB = pow(c.getBlue(), b)

    new MColor(newR.toFloat, newG.toFloat, newB.toFloat, c.getAlpha())
  }
}

object Pow {
  val reads: Reads[Pow] = verifying[Pow](_.`name` == Key.Pow)(Json.reads[Pow])
  val writes: Writes[Pow] = Json.writes[Pow]
}