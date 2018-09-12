package model

import util.operation._
import play.api.libs.json.Json
import util.ImageHelper
import java.awt.image.BufferedImage

case class Selection(name: String, ops: Array[Operation], rectangles: Array[Rectangle], active: Boolean) {

  def apply(img: BufferedImage): BufferedImage = {
    var newImg = img
    
    if(active == true) { 
      for (op <- ops) {
        newImg = op.execute(this, newImg)
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