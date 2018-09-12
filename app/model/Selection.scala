package model

import util.operation._
import play.api.libs.json.Json
import util.ImageHelper
import java.awt.image.BufferedImage
import util.operation.helper._

case class Selection(name: String, ops: Array[Operation], rectangles: Array[Rectangle], active: Boolean) {

  def apply(img: BufferedImage): BufferedImage = {
    var newImg = img
    
    if(active == true) { 
      for (op <- ops) {
        for (rect <- rectangles) {
          for (i <- rect.start.y until rect.end.y) {
            for (j <- rect.start.x until rect.end.x) {
              val color = new MColor(newImg.getRGB(j, i))
              val position = new Position(j, i)

              newImg.setRGB(j, i, op.myexec(ExecuteWrapper(rect, position, img, color)).c.getRGBA())
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