package util.io

import java.io._

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

object IOHelper {
  val inputUrl = "/Users/megazord/Documents/Developer/scala/fp_img_processor/public/images/input/"
  val outputUrl = "/Users/megazord/Documents/Developer/scala/fp_img_processor/public/images/output/"

  def readImageFrom(source: String): BufferedImage = {
    ImageIO.read(new File(inputUrl+source))
  }

  def writeImageTo(destination: String, source: BufferedImage): Boolean = {
    ImageIO.write(source, "png", new File(outputUrl+destination))
    return true
  }
}