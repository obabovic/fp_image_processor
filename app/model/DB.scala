package model

import javax.imageio.ImageIO

import util.operation._
import java.awt.image.BufferedImage
import play.api.libs.json.Json
import java.awt._
import util.io.IOHelper
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

case class DB(layers: Array[Layer], ops: Array[Operation], width: Int, height: Int) {
  def execute(): String = {
    println(width+ " "+ height)
    val rootImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val graphics: Graphics2D = rootImage.getGraphics().asInstanceOf[Graphics2D]
    var dstName: String = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss").format(LocalDateTime.now)+".png"

    layers.foreach { layer =>
      var img = layer.execute()
      
      graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, layer.alpha));
      graphics.drawImage(img, 0, 0, null)
    }
    
    IOHelper.writeImageTo(dstName, rootImage)
    dstName
  }
}

object DB {
  implicit val reads = Json.reads[DB]
  implicit val writes = Json.writes[DB]
}