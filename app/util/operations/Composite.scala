package util.operation

import model._
import util.Key
import play.api.libs.json.{Json, Reads, Writes, JsResult, JsValue, JsSuccess}
import play.api.libs.json.Reads.verifying

import util.operation.helper._

case class Composite(name: String = Key.Composite, operations: Array[Operation], reverse: Boolean = false) extends Operation {
  
  def myexec(e: ExecuteWrapper): ExecuteWrapper = {
    var chain = operations(0).myexec _
    
    if(operations.size > 1) {
      if(reverse) {
        operations.drop(1).foreach(op => {
          chain = chain compose op.myexec _
        })
      } else {  
        operations.drop(1).foreach(op => {
          chain = chain andThen op.myexec _
        })
      }
    }

    chain(e)
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