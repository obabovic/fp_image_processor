package util.operation

import model._
import util.Key
import play.api.libs.json.{Json, Reads, Writes, JsResult, JsValue, JsSuccess}
import play.api.libs.json.Reads.verifying

import util.operation.helper._

case class Composite(name: String = Key.Composite, operations: Array[Operation], reverse: Boolean = false) extends Operation {
  
  def myexec(e: ExecuteWrapper): ExecuteWrapper = {
    def chaining(arr: Array[Operation], current: ExecuteWrapper => ExecuteWrapper): (ExecuteWrapper => ExecuteWrapper) = {
      if (arr.isEmpty) current 
      else chaining(arr.tail, if(reverse) current compose arr.head.myexec _ 
                              else current andThen arr.head.myexec _ )
    }

    chaining(operations, x => x)(e)
  }
}

object Composite {
  val reads: Reads[Composite] = new Reads[Composite] {
    override def reads(json: JsValue): JsResult[Composite] = {
      val name = (json \ "name").as[String]
      val operations = (json \ "ops").as[Array[Operation]]
      val reverse = (json \ "reverse").as[Boolean]
      
      JsSuccess(Composite(name, operations, reverse))
    }
  }

  val writes: Writes[Composite] = new Writes[Composite] {
    override def writes(o: Composite): JsValue = {
      Json.obj(
        "name" -> o.name,
        "ops" -> o.operations,
        "reverse" -> o.reverse)
    }
  }
}