package services

import java.io.File

import com.typesafe.config.{Config, ConfigFactory}
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.http.JavaClient

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import play.api.libs.json._
import com.sksamuel.elastic4s.requests.searches.{DateHistogramInterval, SearchResponse}
import com.sksamuel.elastic4s.{ElasticClient, ElasticProperties, RequestFailure, RequestSuccess}

class HttpClient extends App {

  def getData() = {
    val domain = ConfigFactory.load().getString("MY_DOMAIN")
    val props = ElasticProperties(domain)
    val client = ElasticClient(JavaClient(props))
//    var query = ""
//    if(column == "") {
//      query = "(Men's Clothing) OR (Women's Clothing)"
//    } else if (column == "Man"){
//      query = "(Men's Clothing)"
//    } else if (column == "Woman"){
//      query = "(Women's Clothing)"
//    }

    val rawData = client.execute {
      search("kibana_sample_data_ecommerce")
        .size(0)
        .query(queryStringQuery("(Men's Clothing)").defaultField("category.keyword"))
        .aggs{
          dateHistogramAgg("each_day","order_date").format("yyyy-MM-dd").fixedInterval(86400)
        }
        .sourceInclude("category","order_date")
    }.await


    println("---------------Result------------")
    val rawJson = rawData.result.aggregationsAsString
    val jsonObject:JsValue = Json.parse(rawJson)
    val clothing = (jsonObject \ "each_day" \ "buckets").get

    handleData(clothing)

  }

  def handleData(jsValue: JsValue) = {

    val timeLineOneDay = List((jsValue \ 0 \ "key_as_string").get,(jsValue \ 1 \ "key_as_string").get,(jsValue \ 2 \ "key_as_string").get,
      (jsValue \ 3 \ "key_as_string").get,(jsValue \ 4 \ "key_as_string").get,(jsValue \ 5 \ "key_as_string").get,(jsValue \ 6 \ "key_as_string").get)
    val timeLineTwoDay = List((jsValue \ 1 \ "key_as_string").get,(jsValue \ 3 \ "key_as_string").get,(jsValue \ 5 \ "key_as_string").get,(jsValue \ 6 \ "key_as_string").get)
    val timeLineThreeDay = List((jsValue \ 2 \ "key_as_string").get,(jsValue \ 5 \ "key_as_string").get,(jsValue \ 6 \ "key_as_string").get)
    val jsData = List((jsValue \ 0 \ "doc_count").get,(jsValue \ 1 \ "doc_count").get,(jsValue \ 2 \ "doc_count").get,(jsValue \ 3 \ "doc_count").get,(jsValue \ 4 \ "doc_count").get,
      (jsValue \ 5 \ "doc_count").get,(jsValue \ 6 \ "doc_count").get)

    val jsonValue = Json.toJson(List(timeLineOneDay,jsData))
    jsonValue

  }
}
