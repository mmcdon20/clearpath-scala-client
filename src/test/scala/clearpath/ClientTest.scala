package clearpath

import scala.concurrent.Await
import scala.concurrent.duration._
import akka.actor.ActorSystem

import org.scalatest.FunSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import clearpath.json._

@RunWith(classOf[JUnitRunner])
class ClientTest extends FunSpec {

  implicit val system = ActorSystem("testing-clearpath-client")
  val client = new ClearpathClient
  val timeLimit = 5 seconds

  describe("Most Wanted Method"){
    it("should return a list of WantedCriminal") {
      val wantedFuture = client.mostWanted()
      val result: List[WantedCriminal] = Await.result(wantedFuture, timeLimit)
      assert(!result.isEmpty)
    }
    it("should be able to set a max limit") {
      val wantedFuture1 = client.mostWanted(max = 5)
      val wantedFuture2 = client.mostWanted(max = 1)
      val result1: List[WantedCriminal] = Await.result(wantedFuture1, timeLimit)
      val result2: List[WantedCriminal] = Await.result(wantedFuture2, timeLimit)
      assert(result1.length == 5)
      assert(result2.length == 1)
    }
    it("should be able to order results in asc and desc order") {
      val ascFuture  = client.mostWanted(order = "asc")
      val descFuture = client.mostWanted(order = "desc")
      val asc:  List[WantedCriminal] = Await.result(ascFuture, timeLimit)
      val desc: List[WantedCriminal] = Await.result(descFuture, timeLimit)
      assert(asc != desc)
    }
    it("Should be able to set an offset"){
      val offset1Future = client.mostWanted(offset = 1)
      val offset2Future = client.mostWanted(offset = 2)
      val off1: List[WantedCriminal] = Await.result(offset1Future, timeLimit)
      val off2: List[WantedCriminal] = Await.result(offset2Future, timeLimit)
      assert(off1(1) == off2(0))
      assert(off1 != off2)
    }
    it("Should be able to sort on a field name"){
      val sortFuture     = client.mostWanted(sort = "age")
      val unsortedFuture = client.mostWanted()
      val sort:     List[WantedCriminal] = Await.result(sortFuture, timeLimit)
      val unsorted: List[WantedCriminal] = Await.result(unsortedFuture, timeLimit)
      assert(sort == sort.sortWith(_.age.getOrElse(0) < _.age.getOrElse(0)))
      assert(sort != unsorted)
    }
  }

  describe("Community Events Method"){
    it("should return a list of CommunityEvents") {
      val eventsFuture = client.communityEvents()
      val result: List[CommunityEvent] = Await.result(eventsFuture, timeLimit)
      assert(!result.isEmpty)
    }
    it("should be able to set a max limit") {
      val eventFuture1 = client.communityEvents(max = 5)
      val eventFuture2 = client.communityEvents(max = 1)
      val result1: List[CommunityEvent] = Await.result(eventFuture1, timeLimit)
      val result2: List[CommunityEvent] = Await.result(eventFuture2, timeLimit)
      assert(result1.length == 5)
      assert(result2.length == 1)
    }
    it("should be able to order results in asc and desc order") {
      val ascFuture  = client.communityEvents(order = "asc")
      val descFuture = client.communityEvents(order = "desc")
      val asc:  List[CommunityEvent] = Await.result(ascFuture, timeLimit)
      val desc: List[CommunityEvent] = Await.result(descFuture, timeLimit)
      assert(asc != desc)
    }
    it("Should be able to set an offset"){
      val offset1Future = client.communityEvents(offset = 1)
      val offset2Future = client.communityEvents(offset = 2)
      val off1: List[CommunityEvent] = Await.result(offset1Future, timeLimit)
      val off2: List[CommunityEvent] = Await.result(offset2Future, timeLimit)
      assert(off1(1) == off2(0))
      assert(off1 != off2)
    }
    it("Should be able to sort on a field name"){
      val sortFuture     = client.communityEvents(sort = "location")
      val unsortedFuture = client.communityEvents()
      val sort:     List[CommunityEvent] = Await.result(sortFuture, timeLimit)
      val unsorted: List[CommunityEvent] = Await.result(unsortedFuture, timeLimit)
      assert(sort == sort.sortWith(_.location.getOrElse("") < _.location.getOrElse("")))
      assert(sort != unsorted)
    }
  }
}
