package util.operation

import model._
import util.Key
import play.api.libs.json.Reads.verifying
import play.api.libs.json.{Json, Reads, Writes}
import util.operation.helper._
import java.awt.image.BufferedImage

class Ponder(name: String, w: Int, h: Int, pMat: Array[Array[MColor]]) extends Operation {
  def myexec(e: ExecuteWrapper): MColor = {
    var pos = e.pos
    var img = e.img
    var r = e.rect
    var computationRect = Array[Array[MColor]]()
    var ponderR = 0.0f
    var ponderG = 0.0f
    var ponderB = 0.0f
    
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

    var rMix = 0.0f
    var gMix = 0.0f
    var bMix = 0.0f
    var rPond = 0.0f
    var gPond = 0.0f
    var bPond = 0.0f

    var m = 0
    var n = 0

    for(y <- startAnchor.y until endAnchor.y) {
      n = 0
      for(x <- startAnchor.x until endAnchor.x) {
        rMix = rMix + new MColor(img.getRGB(x, y)).getRed() * pMat(m)(n).getRed()
        rPond = rPond + pMat(m)(n).getRed()
        gMix = gMix + new MColor(img.getRGB(x, y)).getGreen() * pMat(m)(n).getGreen()
        gPond = gPond + pMat(m)(n).getGreen()
        bMix = bMix + new MColor(img.getRGB(x, y)).getBlue() * pMat(m)(n).getBlue()
        bPond = bPond + pMat(m)(n).getBlue()
        
        n = n + 1
      }

      m = m + 1
    }

    ponderR = rMix / rPond
    ponderG = gMix / gPond
    ponderB = bMix / bPond

    return new MColor(ponderR, ponderG, ponderB, e.c.getAlpha())
  }
}

object Ponder {
  val reads: Reads[Ponder] = verifying[Ponder](_.`name` == Key.Ponder)(Json.reads[Ponder])
  val writes: Writes[Ponder] = Json.writes[Ponder]
}