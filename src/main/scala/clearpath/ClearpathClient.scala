package clearpath

import akka.actor.{Props, ActorSystem}
import spray.can.client.DefaultHttpClient
import spray.client.HttpConduit
import spray.httpx.SprayJsonSupport
import spray.util._
import SprayJsonSupport._
import HttpConduit._
import CrimeJsonProtocol._
import spray.json.RootJsonFormat

class ClearpathClient(implicit system: ActorSystem) {
  private val httpClient = DefaultHttpClient(system)
  private val conduit = system.actorOf(Props(new HttpConduit(httpClient, "api1.chicagopolice.org")))

  private def base [T](path: String)(arguments: String)(implicit f: RootJsonFormat[T]) = {
    val pipeline = sendReceive(conduit) ~> unmarshal[List[T]]
    val future   = pipeline(Get(path+arguments))
    future
  }

  def mostWanted(max: Int=10, sort: String="", order: String="", offset: Int=0) = base
    [ WantedCriminal ]
    { "/clearpath/api/1.0/mostWanted/list" }
    { s"?max=$max&sort=$sort&order=$order&offset=$offset" }

  def communityEvents(max: Int=10, sort: String="", order: String="", offset: Int=0) = base
    [ CommunityEvent ]
    { "/clearpath/api/1.0/communityCalendar/events" }
    { s"?max=$max&sort=$sort&order=$order&offset=$offset" }

  def communityCalendars(max: Int=10, sort: String="", order: String="", offset: Int=0) = base
    [ CommunityCalendar ]
    { "/clearpath/api/1.0/communityCalendar" }
    { s"?max=$max&sort=$sort&order=$order&offset=$offset" }

  def crimesMajor(max: Int=10, sort: String="", order: String="", offset: Int=0) = base
    [ Crime ]
    { "/clearpath/api/1.0/crimes/major" }
    { s"?max=$max&sort=$sort&order=$order&offset=$offset" }

  def crimesNearby(x: Int, y: Int, radius: Int=1000, max: Int=10, sort: String="", order: String="", offset: Int=0) = base
    [ Crime ]
    { "/clearpath/api/1.0/crimes/nearbyXY" }
    { s"?x=$x&y=$y&radius=$radius&max=$max&sort=$sort&order=$order&offset=$offset" }

  def crimesRdNo(rdNo: Int, max: Int=10, sort: String="", order: String="", offset: Int=0) = base
    [ Crime ]
    { "/clearpath/api/1.0/crimes/rdNo" }
    { s"?rdNo=$rdNo&max=$max&sort=$sort&order=$order&offset=$offset" }

}
