package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import util.operation.helper._
import scala.math.log

case class Log(name: String = Key.Log) extends NoArg {
  def myexec(e: ExecuteWrapper): ExecuteWrapper = {
    val c = e.c
    val newR = log(c.getRed())
    val newG = log(c.getGreen())
    val newB = log(c.getBlue())

    ExecuteWrapper(e.rect, e.pos, e.img, new MColor(newR.toFloat, newG.toFloat, newB.toFloat, c.getAlpha()))
  }
}

object Log {
  val reads: Reads[Log] = verifying[Log](_.`name` == Key.Log)(Json.reads[Log])
  val writes: Writes[Log] = Json.writes[Log]
}