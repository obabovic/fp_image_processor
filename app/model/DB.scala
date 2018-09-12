package model

import javax.imageio.ImageIO

import util.operation._
import java.awt.image.BufferedImage
import play.api.libs.json.Json
import java.awt._
import util.io.IOHelper

case class DB(layers: Array[Layer], width: Int, height: Int) {
  def execute(): String = {
    val rootImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val graphics: Graphics2D = rootImage.getGraphics().asInstanceOf[Graphics2D]
    var dstName: String = ""

    layers.foreach { layer =>
      var img = layer.execute()
      
      dstName = dstName ++ layer.imagePath
      graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, layer.alpha));
      graphics.drawImage(img, 0, 0, null)
    }
    
    IOHelper.writeImageTo("mojti.png", rootImage)
    dstName
  }
}

object DB {
  implicit val reads = Json.reads[DB]
  implicit val writes = Json.writes[DB]
}