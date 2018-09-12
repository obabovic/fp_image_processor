package model

import javax.imageio.ImageIO

import util.io._
import util.operation._
import java.awt.image.BufferedImage
import play.api.libs.json.Json

case class Layer(id: Int, imagePath: String, selections: Array[Selection], alpha: Float, active: Boolean) {
  
  def execute(): BufferedImage = {
    var image = IOHelper.readImageFrom(imagePath)
    var newImg = image
    if(active == true) {
      for(sel <- selections) {
        newImg = sel.apply(newImg)
      }

      newImg
    } else {
      null
    }
  }
}

object Layer {
  private var id = 0

  def generateID(): Int = {
    id = id + 1
    id
  }

  implicit val reads = Json.reads[Layer]
  implicit val writes = Json.writes[Layer]
}