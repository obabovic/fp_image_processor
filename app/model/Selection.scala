package model

import util.operation._
import play.api.libs.json.Json
import util.ImageHelper
import java.awt.image.BufferedImage
import util.operation.helper._

case class Selection(name: String, ops: Array[Operation], rectangles: Array[Rectangle], active: Boolean) {
  val functions: Array[ExecuteWrapper => ExecuteWrapper] = ops.map(op => Operation.createFunction(op))

  def execute(img: BufferedImage): BufferedImage = if (active == true) {
    var newImg = img

    functions.foreach(f => {
      rectangles.foreach(rect => {
        for (i <- rect.start.y until rect.end.y; j <- rect.start.x until rect.end.x) {
          val color = new MColor(newImg.getRGB(j, i))
          val position = new Position(j, i)

          newImg.setRGB(j, i, f(ExecuteWrapper(rect, position, img, color)).c.getRGBA())
        }
      })
    })

    newImg
  } else {
    img
  }
}

object Selection {
  implicit val reads = Json.reads[Selection]
  implicit val writes = Json.writes[Selection]
}