package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import util.operation.helper._

case class InvSub(name: String = Key.Invsub, mc: MColor) extends Operation {
  def myexec(e: ExecuteWrapper): ExecuteWrapper = {
    val c = e.c
    val newR = mc.getRed() - c.getRed()
    val newG = mc.getGreen() - c.getGreen()
    val newB = mc.getBlue() - c.getBlue()

    ExecuteWrapper(e.rect, e.pos, e.img, new MColor(newR, newG, newB, c.getAlpha()))
  }
}

object InvSub {
  val reads: Reads[InvSub] = verifying[InvSub](_.`name` == Key.Invsub)(Json.reads[InvSub])
  val writes: Writes[InvSub] = Json.writes[InvSub]
}