package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import util.operation.helper._
import java.awt.image.BufferedImage

class Mediana(name: String, w: Int, h: Int) extends Operation {

  def myexec(e: ExecuteWrapper): MColor = {
    var pos = e.pos
    var img = e.img
    var r = e.rect
    var computationRect = Array[Array[MColor]]()
    var arr = Array[MColor]()
    var rSorted = Array[MColor]()
    var gSorted = Array[MColor]()
    var bSorted = Array[MColor]()
    var medianR = 0.0f
    var medianG = 0.0f
    var medianB = 0.0f

    var startX = 0
    var startY = 0
    var endX = 0
    var endY = 0

    startY = if (pos.y - h > r.start.y) {
      pos.y - h
    } else {
      pos.y
    }

    startX = if (pos.x - w > r.start.x) {
      pos.x - w
    } else {
      pos.x
    }

    endY = if (pos.y + h < r.end.y) {
      pos.y + h
    } else {
      r.end.y
    }

    endX = if (pos.x + w < r.end.x) {
      pos.x + w
    } else {
      r.end.x
    }

    var startAnchor = new Position(startX, startY)
    var endAnchor = new Position(endX, endY)
    
    for(x <- startAnchor.x until endAnchor.x) {
      for(y <- startAnchor.y until endAnchor.y) {
        arr = new MColor(img.getRGB(x, y)) +: arr
      }
    }

    arr = e.c +: arr

    rSorted = arr.sortWith(_.getRed() < _.getRed()).clone
    gSorted = arr.sortWith(_.getGreen() < _.getGreen()).clone
    bSorted = arr.sortWith(_.getBlue() < _.getBlue()).clone

    medianR = if(rSorted.size % 2 == 1) rSorted(rSorted.size/2).getRed() else (rSorted(rSorted.size/2-1).getRed() + rSorted(rSorted.size/2-1).getRed())/2
    medianG = if(rSorted.size % 2 == 1) gSorted(gSorted.size/2).getGreen() else (gSorted(gSorted.size/2-1).getGreen() + gSorted(gSorted.size/2-1).getGreen())/2
    medianB = if(rSorted.size % 2 == 1) bSorted(bSorted.size/2).getBlue() else (bSorted(bSorted.size/2-1).getBlue() + bSorted(bSorted.size/2-1).getBlue())/2

    return new MColor(medianR, medianG, medianB, e.c.getAlpha())
  }
}


object Mediana {
  val reads: Reads[Mediana] = verifying[Mediana](_.`name` == Key.Mediana)(Json.reads[Mediana])
  val writes: Writes[Mediana] = Json.writes[Mediana]
}