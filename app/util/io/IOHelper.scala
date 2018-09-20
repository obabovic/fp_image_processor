package util.io

import java.io._

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import model._
import play.api.libs.json._
import java.io._

object IOHelper {
  val inputUrl = "/Users/megazord/Documents/Developer/scala/fp_img_processor/public/images/input/"
  val outputUrl = "/Users/megazord/Documents/Developer/scala/fp_img_processor/public/images/output/"
  val saveUrl = "/Users/megazord/Documents/Developer/scala/fp_img_processor/app/util/io/save.txt"

  def readImageFrom(source: String): BufferedImage = {
    ImageIO.read(new File(inputUrl+source))
  }

  def writeImageTo(destination: String, source: BufferedImage): Boolean = {
    ImageIO.write(source, "png", new File(outputUrl+destination))
    return true
  }

  def saveConfiguration(src: DB) = {
    val file = new File(saveUrl)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(Json.toJson(src).toString)
    bw.close()
  }

  def loadConfiguration(): JsValue = {
    val file = new File(saveUrl)
    val stream = new FileInputStream(file)
    val json = try {  Json.parse(stream) } finally { stream.close() }
    json
  }
}