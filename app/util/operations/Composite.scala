package util.operation

import model._
import util.Key
import play.api.libs.json.{Json, Reads, Writes}
import play.api.libs.json.Reads.verifying
import util.operation.helper._

case class Composite(name: String, ops: Array[Operation]) extends Operation {
  
  def myexec(e: ExecuteWrapper): ExecuteWrapper = {
    var composition = ops(0).myexec _
    
    if(ops.size > 1) {
      ops.drop(1).foreach(op => {
        composition = composition compose op.myexec _
      })
    }

    composition(e)
  }
}

object Composite {
  val reads: Reads[Composite] = verifying[Composite](_.`name` == Key.Composite)(Json.reads[Composite])
  val writes: Writes[Composite] = Json.writes[Composite]
}