package util.operation

import model._
import util.operation.helper._
import java.awt.image.BufferedImage


abstract case class Filter(name: String, w: Int, h: Int) extends Operation {}