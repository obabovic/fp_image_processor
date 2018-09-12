package controllers

import javax.inject._
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
          Ok(Json.toJson("{\"URL\":"+ url +"}"))
        }
      }.recoverTotal {
        e => BadRequest("Detected error:"+ JsError.toJson(e))
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
  }
}
