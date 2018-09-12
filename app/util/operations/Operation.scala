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

  def execute(sel: Selection, img: BufferedImage): BufferedImage = {
    var res = img

    if(child != null) {
      res = child.execute(sel, res)
    } 

    for (rect <- sel.rectangles) {
      val xStart = rect.start.x
      val xEnd = rect.end.x
      val yStart = rect.start.y
      val yEnd = rect.end.y
      
      for (i <- yStart until yEnd) {
        for (j <- xStart until xEnd) {
          val color = new MColor(res.getRGB(j, i))

          this match {
            case Filter(name, w, h) =>
              res.setRGB(j, i, myexec(ExecuteWrapper(rect, new Position(j, i), res, color)).getRGBA())
            case default => 
              res.setRGB(j, i, myexec(ExecuteWrapper(null, null, null, color)).getRGBA());
          }
        }
      }
    }

    res
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
    Greyscale.reads.map(identity[Operation]) // orElse
    // Mediana.reads.map(identity[Operation]) orElse
    // Ponder.reads.map(identity[Operation])

  implicit val operationWrites: Writes[Operation] = new Writes[Operation] {
    def writes(op: Operation): JsValue = {
      op match {
        case oper: Add => Json.toJson(oper)(Add.writes)
        case oper: Sub => Json.toJson(oper)(Sub.writes)
        case oper: InvSub => Json.toJson(oper)(InvSub.writes)
        case oper: Mul => Json.toJson(oper)(Mul.writes)
        case oper: Inv => Json.toJson(oper)(Inv.writes)
        case oper: Div => Json.toJson(oper)(Div.writes)
        case oper: InvDiv => Json.toJson(oper)(InvDiv.writes)
        case oper: Pow => Json.toJson(oper)(Pow.writes)
        case oper: Log => Json.toJson(oper)(Log.writes)
        case oper: Min => Json.toJson(oper)(Min.writes)
        case oper: Max => Json.toJson(oper)(Max.writes)
        case oper: Greyscale => Json.toJson(oper)(Greyscale.writes)
        case _ => Json.obj("error" -> "Unknown operation format")
      }
    }
  }
}