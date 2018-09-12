package util.operation

import model._
import util.Key
import play.api.libs.json.{Json, Reads, Writes, JsResult, JsValue, JsSuccess}
import play.api.libs.json.Reads.verifying

import util.operation.helper._

case class Composite(name: String = Key.Composite, operations: Array[Operation]) extends Operation {
  
  def myexec(e: ExecuteWrapper): ExecuteWrapper = {
    var composition = operations(0).myexec _
    
    if(operations.size > 1) {
      operations.drop(1).foreach(op => {
        composition = composition compose op.myexec _
      })
    }

    composition(e)
  }
}

object Composite {
  val reads: Reads[Composite] = new Reads[Composite] {
    override def reads(json: JsValue): JsResult[Composite] = {
      val name = (json \ "name").as[String]
      val operations = (json \ "operations").as[Array[Operation]]
      JsSuccess(Composite(name, operations))
    }
  }

  val writes: Writes[Composite] = new Writes[Composite] {
    override def writes(o: Composite): JsValue = {
      Json.obj(
        "name" -> o.name,
        "operations" -> o.operations)
    }
  }
}