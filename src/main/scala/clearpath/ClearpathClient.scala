package clearpath

import akka.actor.ActorSystem
import scala.concurrent.Future
import spray.client.pipelining._
import spray.httpx.SprayJsonSupport
import spray.json.JsonFormat

import SprayJsonSupport._
import clearpath.json._
import CrimeJsonProtocol._

class ClearpathClient(implicit system: ActorSystem) {
  import system.dispatcher
  private val base = "http://api1.chicagopolice.org/clearpath/api/1.0/"

  private def get [T](method: String)(arguments: String)(implicit f: JsonFormat[T]): Future[List[T]] = {
    val pipeline = sendReceive ~> unmarshal[List[T]]
    val future   = pipeline(Get(base+method+arguments.replaceAll(" ","+")))
    future
  }

  def mostWanted(effectiveDate: String="", district: String="", max: Int=10, sort: String="", offset: Int=0) = get
    [ WantedCriminal ]
    { "mostWanted/list" }
    { s"?effectiveDate=$effectiveDate&district=$district&max=$max&sort=$sort&offset=$offset" }

  def communityEvents(max: Int=10, sort: String="", offset: Int=0) = get
    [ CommunityEvent ]
    { "communityCalendar/events" }
    { s"?max=$max&sort=$sort&offset=$offset" }

  def communityCalendars(max: Int=10, sort: String="", offset: Int=0) = get
    [ CommunityCalendar ]
    { "communityCalendar" }
    { s"?max=$max&sort=$sort&offset=$offset" }

  def crimesMajor(block: String="", max: Int=10, sort: String="", offset: Int=0) = get
    [ Crime ]
    { "crimes/major" }
    { s"?block=$block&max=$max&sort=$sort&offset=$offset" }

  def crimesNearby(x: Int, y: Int, radius: Int=1000, max: Int=10, sort: String="", offset: Int=0) = get
    [ Crime ]
    { "crimes/nearbyXY" }
    { s"?x=$x&y=$y&radius=$radius&max=$max&sort=$sort&offset=$offset" }

  def crimesRdNo(rdNo: Int, max: Int=10, sort: String="", offset: Int=0) = get
    [ Crime ]
    { "crimes/rdNo" }
    { s"?rdNo=$rdNo&max=$max&sort=$sort&offset=$offset" }

  def crimesType(primary: String, max: Int=10, sort: String="", offset: Int=0) = get
    [ Crime ]
    { "crimes/type" }
    { s"?primary=$primary&max=$max&sort=$sort&offset=$offset" }

  def crimesList(max: Int=10, sort: String="", offset: Int=0) = get
    [ Crime ]
    { "crimes/list" }
    { s"?max=$max&sort=$sort&offset=$offset" }

  def mugshots(warrantNo: String) = get
    [ Mugshot ]
    { "mugshots" }
    { s"?warrantNo=$warrantNo" }

}
