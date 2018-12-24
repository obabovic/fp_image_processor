package util.operation

import model._
import util.Key
import play.api.libs.json.{Json, Reads, Writes, JsResult, JsValue, JsSuccess}
import play.api.libs.json.Reads.verifying

import util.operation.helper._

case class Composite(name: String = Key.Composite, ops: Array[Operation], reverse: Boolean = false) extends Operation {
  def myexec(operations: Array[Operation], reverse: Boolean = false): ExecuteWrapper => ExecuteWrapper = {
    def chaining(arr: Array[Operation], current: ExecuteWrapper => ExecuteWrapper, reverse: Boolean): (ExecuteWrapper => ExecuteWrapper) = {
      if (arr.isEmpty) current 
      else chaining(arr.tail, if(reverse) current compose Operation.createFunction(arr.head) 
                              else current andThen Operation.createFunction(arr.head), reverse)
    }

    chaining(operations, x => x, reverse)
  }
}

object Composite {
  val reads: Reads[Composite] = new Reads[Composite] {
    override def reads(json: JsValue): JsResult[Composite] = {
      val name = (json \ "name").as[String]
      val ops = (json \ "ops").as[Array[Operation]]
      val reverse = (json \ "reverse").as[Boolean]
      
      JsSuccess(Composite(name, ops, reverse))
    }
  }

  val writes: Writes[Composite] = new Writes[Composite] {
    override def writes(o: Composite): JsValue = {
      Json.obj(
        "name" -> o.name,
        "ops" -> o.ops,
        "reverse" -> o.reverse)
    }
  }
}