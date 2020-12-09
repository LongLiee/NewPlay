package controllers
import play.api.data._
import services.HttpClientExample
import services.HttpClient

import javax.inject._
import play.api.mvc._
import play.api.data.Forms._
import play.api.i18n.Messages._
import play.api.libs._
import play.api.i18n.I18nSupport
import play.api.libs.json.Json

import javax.inject._
import play.api.mvc.{AbstractController, ControllerComponents}



case class BasicForm( typeCate:String, typeDay:String )



@Singleton
class TaskList1 @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {


  val form = Form(mapping(
    "Category" -> text,
    "Day" -> text
  )(BasicForm.apply)(BasicForm.unapply))

//  val userData = formBasic.bindFromRequest.get



//  def selectForm = Action { implicit request =>
//    val field1 = ""
//    val field2 =  ""
//    val jsonData = Json.obj()
//    val result = field1 ++ field2
//        Ok(views.html.index(result, jsonData))
//      }
//  def selectForm () = Action { implicit request =>
//    Ok(views.html.TaskList1(form))
//}
//  def simpleFormPost() = Action { implicit request =>
//      val formData:BasicForm = form.bindFromRequest().get
//    formData.toString
//  }
}
