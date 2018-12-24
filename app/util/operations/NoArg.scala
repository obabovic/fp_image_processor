package util.operation

import model._
import util.operation.helper._

trait NoArg extends Operation {
  def myexec(e: ExecuteWrapper): ExecuteWrapper
}