package model

import util.operation._
import play.api.libs.json.Json
import util.ImageHelper
import java.awt.image.BufferedImage
import util.operation.helper._

case class Selection(name: String, ops: Array[Operation], rectangles: Array[Rectangle], active: Boolean) {

  def execute(img: BufferedImage): BufferedImage = {
    def createFunction(o: Operation): ExecuteWrapper => ExecuteWrapper = {
      o match {
        case a: Arithmetic => {
          a.myexec(a.mc)
        }

        case n: NoArg => {
          n.myexec _
        }

        case f: Filter => {
          f.myexec(f.w, f.h, f.pMat)
        }

        case c: Composite => {
          def chaining(arr: Array[Operation], current: ExecuteWrapper => ExecuteWrapper, reverse: Boolean): (ExecuteWrapper => ExecuteWrapper) = {
            if (arr.isEmpty) current 
            else chaining(arr.tail, if(reverse) current compose createFunction(arr.head) 
                                    else current andThen createFunction(arr.head), reverse)
          }

          chaining(c.operations, x => x, c.reverse)
        }
      }
    }

    var newImg = img
    var fArr: Array[ExecuteWrapper => ExecuteWrapper] = Array()

    for(op <- ops) {
      fArr = fArr :+ createFunction(op)
    }

    if(active == true) { 
      for (op <- fArr) {
        for (rect <- rectangles) {
          for (i <- rect.start.y until rect.end.y) {
            for (j <- rect.start.x until rect.end.x) {
              val color = new MColor(newImg.getRGB(j, i))
              val position = new Position(j, i)

              newImg.setRGB(j, i, op(ExecuteWrapper(rect, position, img, color)).c.getRGBA())
            }
          }
        }

        newImg
      }

      newImg
    } else {
      img
    }
  }
}

object Selection {
  implicit val reads = Json.reads[Selection]
  implicit val writes = Json.writes[Selection]
}