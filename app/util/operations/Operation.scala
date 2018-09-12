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
  var child: Operation = null

  protected def myexec(e: ExecuteWrapper): MColor

  def execute(sel: Selection, image: BufferedImage): BufferedImage = {
    var img = image

    if(child != null) {
      img = child.execute(sel, img)
    }

    for (rect <- sel.rectangles) {
      for (i <- rect.start.y until rect.end.y) {
        for (j <- rect.start.x until rect.end.x) {
          val color = new MColor(img.getRGB(j, i))
          val position = new Position(j, i)

          img.setRGB(j, i, myexec(ExecuteWrapper(rect, position, img, color)).getRGBA())
        }
      }
    }

    img
  }
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
    Ponder.reads.map(identity[Operation])

  implicit val operationWrites: Writes[Operation] = new Writes[Operation] {
    def writes(op: Operation): JsValue = {
      op match {
        case oper: Add => Json.toJson(oper)(Add.writes)
        case oper: Sub => Json.toJson(oper)(Sub.writes)
        case oper: InvSub => Json.toJson(oper)(InvSub.writes)
        case oper: Inv => Json.toJson(oper)(Inv.writes)
        case oper: Mul => Json.toJson(oper)(Mul.writes)
        case oper: Div => Json.toJson(oper)(Div.writes)
        case oper: InvDiv => Json.toJson(oper)(InvDiv.writes)
        case oper: Pow => Json.toJson(oper)(Pow.writes)
        case oper: Log => Json.toJson(oper)(Log.writes)
        case oper: Min => Json.toJson(oper)(Min.writes)
        case oper: Max => Json.toJson(oper)(Max.writes)
        case oper: Greyscale => Json.toJson(oper)(Greyscale.writes)
        case oper: Mediana => Json.toJson(oper)(Mediana.writes)
        case oper: Ponder => Json.toJson(oper)(Ponder.writes)
        case _ => Json.obj("error" -> "The given operation name is invalid.")
      }
    }
  }
}