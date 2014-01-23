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
  private val base = "http://api1.chicagopolice.org"

  private def get [T](method: String)(arguments: String)(implicit f: JsonFormat[T]): Future[List[T]] = {
    val pipeline = sendReceive ~> unmarshal[List[T]]
    val future   = pipeline(Get(base+method+arguments))
    future
  }

  def mostWanted(effectiveDate: String="", district: String="", max: Int=10, sort: String="", offset: Int=0) = get
    [ WantedCriminal ]
    { "/clearpath/api/1.0/mostWanted/list" }
    { s"?effectiveDate=$effectiveDate&district=$district&max=$max&sort=$sort&offset=$offset" }

  def communityEvents(max: Int=10, sort: String="", offset: Int=0) = get
    [ CommunityEvent ]
    { "/clearpath/api/1.0/communityCalendar/events" }
    { s"?max=$max&sort=$sort&offset=$offset" }

  def communityCalendars(max: Int=10, sort: String="", offset: Int=0) = get
    [ CommunityCalendar ]
    { "/clearpath/api/1.0/communityCalendar" }
    { s"?max=$max&sort=$sort&offset=$offset" }

  def crimesMajor(block: String="", max: Int=10, sort: String="", offset: Int=0) = get
    [ Crime ]
    { "/clearpath/api/1.0/crimes/major" }
    { s"?block=$block&max=$max&sort=$sort&offset=$offset" }

  def crimesNearby(x: Int, y: Int, radius: Int=1000, max: Int=10, sort: String="", offset: Int=0) = get
    [ Crime ]
    { "/clearpath/api/1.0/crimes/nearbyXY" }
    { s"?x=$x&y=$y&radius=$radius&max=$max&sort=$sort&offset=$offset" }

  def crimesRdNo(rdNo: Int, max: Int=10, sort: String="", offset: Int=0) = get
    [ Crime ]
    { "/clearpath/api/1.0/crimes/rdNo" }
    { s"?rdNo=$rdNo&max=$max&sort=$sort&offset=$offset" }

  def crimesType(primary: String, max: Int=10, sort: String="", offset: Int=0) = get
    [ Crime ]
    { "/clearpath/api/1.0/crimes/type" }
    { s"?primary=$primary&max=$max&sort=$sort&offset=$offset" }

  def crimesList(max: Int=10, sort: String="", offset: Int=0) = get
    [ Crime ]
    { "/clearpath/api/1.0/crimes/list" }
    { s"?max=$max&sort=$sort&offset=$offset" }

  def mugshots(warrantNo: String, max: Int=10, sort: String="", offset: Int=0) = get
    [ Mugshot ]
    { "/clearpath/api/1.0/mugshots" }
    { s"?warrantNo=$warrantNo&max=$max&sort=$sort&offset=$offset" }

}
