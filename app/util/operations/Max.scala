package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import util.operation.helper._
import scala.math.max


case class Max(name: String = Key.Max, mc: MColor) extends Operation {
  def myexec(e: ExecuteWrapper): MColor = {
    val c = e.c
    val newR = max(c.getRed(), mc.getRed())
    val newG = max(c.getGreen(), mc.getGreen())
    val newB = max(c.getBlue(), mc.getBlue())

    new MColor(newR, newG, newB, mc.getAlpha())
  }
}

object Max {
  val reads: Reads[Max] = verifying[Max](_.`name` == Key.Max)(Json.reads[Max])
  val writes: Writes[Max] = Json.writes[Max]
}