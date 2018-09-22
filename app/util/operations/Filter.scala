package util.operation

import model._
import util.operation.helper._

trait Filter extends Operation {
  val w: Int
  val h: Int
  val pMat: Array[Array[MColor]]
  
  def myexec(w: Int, h: Int, pMat: Array[Array[MColor]])(e: ExecuteWrapper): ExecuteWrapper
}