package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import util.operation.helper._

case class Inv(name: String = Key.Inv) extends Operation {
  def myexec(e: ExecuteWrapper): ExecuteWrapper = {
    val c = e.c
    val newR = 1.0f - c.getRed()
    val newG = 1.0f - c.getGreen()
    val newB = 1.0f - c.getBlue()

    ExecuteWrapper(e.rect, e.pos, e.img, new MColor(newR, newG, newB, c.getAlpha()))
  }
}

object Inv {
  val reads: Reads[Inv] = verifying[Inv](_.`name` == Key.Inv)(Json.reads[Inv])
  val writes: Writes[Inv] = Json.writes[Inv]
}