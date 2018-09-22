package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import play.api.libs.json.JsValue
import util.operation.helper._
import java.awt.image.BufferedImage

trait Operation {
  val name: String
}


object Operation {
    implicit val operationReads: Reads[Operation] =
        Add.reads.map(identity[Operation]) orElse
        Sub.reads.map(identity[Operation]) orElse
        InvSub.reads.map(identity[Operation]) orElse
        Inv.reads.map(identity[Operation]) orElse
        Mul.reads.map(identity[Operation]) orElse
        Div.reads.map(identity[Operation]) orElse
        InvDiv.reads.map(identity[Operation]) orElse
        Pow.reads.map(identity[Operation]) orElse
        Log.reads.map(identity[Operation]) orElse
        Min.reads.map(identity[Operation]) orElse
        Max.reads.map(identity[Operation]) orElse
        Greyscale.reads.map(identity[Operation]) orElse
        Mediana.reads.map(identity[Operation]) orElse
        Ponder.reads.map(identity[Operation]) orElse
        Composite.reads.map(identity[Operation]) 

    implicit val operationWrites: Writes[Operation] = new Writes[Operation] {
        def writes(op: Operation): JsValue = {
          op match {
            case operation: Add => Json.toJson(operation)(Add.writes)
            case operation: Sub => Json.toJson(operation)(Sub.writes)
            case operation: InvSub => Json.toJson(operation)(InvSub.writes)
            case operation: Inv => Json.toJson(operation)(Inv.writes)
            case operation: Mul => Json.toJson(operation)(Mul.writes)
            case operation: Div => Json.toJson(operation)(Div.writes)
            case operation: InvDiv => Json.toJson(operation)(InvDiv.writes)
            case operation: Pow => Json.toJson(operation)(Pow.writes)
            case operation: Log => Json.toJson(operation)(Log.writes)
            case operation: Min => Json.toJson(operation)(Min.writes)
            case operation: Max => Json.toJson(operation)(Max.writes)
            case operation: Greyscale => Json.toJson(operation)(Greyscale.writes)
            case operation: Mediana => Json.toJson(operation)(Mediana.writes)
            case operation: Ponder => Json.toJson(operation)(Ponder.writes)
            case operation: Composite => Json.toJson(operation)(Composite.writes)
            case _ => Json.obj("error" -> "The given operation name is invalid.")
            }
        }
    }
}