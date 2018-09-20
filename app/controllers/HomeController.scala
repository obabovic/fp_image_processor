package controllers

import javax.inject._
import util.io.IOHelper
import play.api._
import play.api.mvc._
import play.api.libs.json._
import model._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def executeRequest() = Action { implicit request: Request[AnyContent] =>
    request.body.asJson.map { json =>
      json.validate[DB].map {  
        case (content) => {
          println(content)
          var url = content.execute()
          IOHelper.saveConfiguration(content)
          Ok(Json.toJson(url))
        }
      }.recoverTotal {
        e => BadRequest("Detected error:"+ JsError.toJson(e))
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
  }

  def loadConfiguration() = Action { implicit request: Request[AnyContent] =>
    Ok(IOHelper.loadConfiguration())
  }
}
