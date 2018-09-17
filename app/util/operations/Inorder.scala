package util.operation

import model._
import util.Key
import play.api.libs.json.{Json, Reads, Writes, JsResult, JsValue, JsSuccess}
import play.api.libs.json.Reads.verifying

import util.operation.helper._

case class Inorder(name: String = Key.Inorder, operations: Array[Operation]) extends Operation {
  
  def myexec(e: ExecuteWrapper): ExecuteWrapper = {
    var inorder = operations(0).myexec _
    
    if(operations.size > 1) {
      operations.drop(1).foreach(op => {
        inorder = inorder andThen op.myexec _
      })
    }

    inorder(e)
  }
}

object Inorder {
  val reads: Reads[Inorder] = new Reads[Inorder] {
    override def reads(json: JsValue): JsResult[Inorder] = {
      val name = (json \ "name").as[String]
      val operations = (json \ "operations").as[Array[Operation]]
      JsSuccess(Inorder(name, operations))
    }
  }

  val writes: Writes[Inorder] = new Writes[Inorder] {
    override def writes(o: Inorder): JsValue = {
      Json.obj(
        "name" -> o.name,
        "operations" -> o.operations)
    }
  }
}