package util.operation

import model._
import util.operation.helper._

trait Arithmetic extends Operation {
  val mc: MColor

  def myexec(const: MColor)(e: ExecuteWrapper): ExecuteWrapper
}