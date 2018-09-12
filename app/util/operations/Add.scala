package util.operation

import model._
import util.Key
import play.api.libs.json.{Json, Reads, Writes}
import play.api.libs.json.Reads.verifying
import util.operation.helper._

case class Add(name: String, mc: MColor) extends Operation {
  
  def myexec(e: ExecuteWrapper): MColor = {
    val c = e.c
    val newR = c.getRed() + mc.getRed()
    val newG = c.getGreen() + mc.getGreen()
    val newB = c.getBlue() + mc.getBlue()

    new MColor(newR, newG, newB, c.getAlpha())
  }
}

object Add {
  val reads: Reads[Add] = verifying[Add](_.`name` == Key.Add)(Json.reads[Add])
  val writes: Writes[Add] = Json.writes[Add]
}