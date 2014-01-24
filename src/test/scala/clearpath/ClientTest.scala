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
  val timeLimit = 20.seconds

  describe("Most Wanted Method") {
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
    it("Should be able to set an offset") {
      val offset1Future = client.mostWanted(offset = 1)
      val offset2Future = client.mostWanted(offset = 2)
      val off1: List[WantedCriminal] = Await.result(offset1Future, timeLimit)
      val off2: List[WantedCriminal] = Await.result(offset2Future, timeLimit)
      assert(off1(1) == off2(0))
      assert(off1 != off2)
    }
    it("Should be able to sort on a field name") {
      val sortedFuture = client.mostWanted(sort = "age")
      val sorted: List[WantedCriminal] = Await.result(sortedFuture, timeLimit)
      assert(sorted == sorted.sortBy(_.age))
    }
    it("Should be able to set an effective date") {
      val datesFuture = client.mostWanted(effectiveDate = "05-01-2013")
      val dates: List[WantedCriminal] = Await.result(datesFuture, timeLimit)
      assert(dates.forall(_.effectiveDate.get contains "May-01-2013"))
      assert(!dates.forall(_.effectiveDate.get contains "July-13-2012"))
    }
    it("Should be able to set a district") {
      val districtFuture = client.mostWanted(district = "18")
      val district: List[WantedCriminal] = Await.result(districtFuture, timeLimit)
      assert(!district.isEmpty) // there is no district field to compare against
    }
  }

  describe("Community Events Method") {
    it("should return a list of CommunityEvent") {
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
    it("Should be able to set an offset") {
      val offset1Future = client.communityEvents(offset = 1)
      val offset2Future = client.communityEvents(offset = 2)
      val off1: List[CommunityEvent] = Await.result(offset1Future, timeLimit)
      val off2: List[CommunityEvent] = Await.result(offset2Future, timeLimit)
      assert(off1(1) == off2(0))
      assert(off1 != off2)
    }
    it("Should be able to sort on a field name") {
      val sortedFuture = client.communityEvents(sort = "location")
      val sorted: List[CommunityEvent] = Await.result(sortedFuture, timeLimit)
      assert(sorted == sorted.sortBy(_.location))
    }
  }

  describe("Community Calendars Method") {
    it("should return a list of CommunityCalender") {
      val calendarsFuture = client.communityCalendars()
      val result: List[CommunityCalendar] = Await.result(calendarsFuture, timeLimit)
      assert(!result.isEmpty)
    }
    it("should be able to set a max limit") {
      val calendarFuture1 = client.communityCalendars(max = 5)
      val calendarFuture2 = client.communityCalendars(max = 1)
      val result1: List[CommunityCalendar] = Await.result(calendarFuture1, timeLimit)
      val result2: List[CommunityCalendar] = Await.result(calendarFuture2, timeLimit)
      assert(result1.length == 5)
      assert(result2.length == 1)
    }
    it("Should be able to set an offset") {
      val offset1Future = client.communityCalendars(offset = 1)
      val offset2Future = client.communityCalendars(offset = 2)
      val off1: List[CommunityCalendar] = Await.result(offset1Future, timeLimit)
      val off2: List[CommunityCalendar] = Await.result(offset2Future, timeLimit)
      assert(off1(1) == off2(0))
      assert(off1 != off2)
    }
    it("Should be able to sort on a field name") {
      val sortedFuture = client.communityCalendars(sort = "name")
      val sorted: List[CommunityCalendar] = Await.result(sortedFuture, timeLimit)
      assert(sorted == sorted.sortBy(_.name.map(_.toLowerCase)))
    }
  }

  describe("Crimes Major Method") {
    it("should return a list of Crime") {
      val crimeFuture = client.crimesMajor()
      val result: List[Crime] = Await.result(crimeFuture, timeLimit)
      assert(!result.isEmpty)
    }
    it("should be able to set a max limit") {
      val crimeFuture1 = client.crimesMajor(max = 5)
      val crimeFuture2 = client.crimesMajor(max = 1)
      val result1: List[Crime] = Await.result(crimeFuture1, timeLimit)
      val result2: List[Crime] = Await.result(crimeFuture2, timeLimit)
      assert(result1.length == 5)
      assert(result2.length == 1)
    }
    it("Should be able to set an offset") {
      val offset1Future = client.crimesMajor(offset = 1)
      val offset2Future = client.crimesMajor(offset = 2)
      val off1: List[Crime] = Await.result(offset1Future, timeLimit)
      val off2: List[Crime] = Await.result(offset2Future, timeLimit)
      assert(off1(1) == off2(0))
      assert(off1 != off2)
    }
    it("Should be able to sort on a field name") {
      val sortedFuture = client.crimesMajor(sort = "block")
      val sorted: List[Crime] = Await.result(sortedFuture, timeLimit)
      assert(sorted == sorted.sortBy(_.block))
    }
    it("Should be able to set a block") {
      val blockFuture = client.crimesMajor(block = "026XX S INDIANA AV")
      val block: List[Crime] = Await.result(blockFuture, timeLimit)
      assert(block.forall(_.block.get contains "026XX S INDIANA AV"))
      assert(!block.forall(_.block.get contains "029XX S MICHIGAN AV"))
    }
  }

  describe("Crimes Nearby Method") {
    it("should return a list of Crime") {
      val crimeFuture = client.crimesNearby(1178459,1886515)
      val result: List[Crime] = Await.result(crimeFuture, timeLimit)
      assert(!result.isEmpty)
    }
    it("should be able to set a max limit") {
      val crimeFuture1 = client.crimesNearby(1178459,1886515, max = 5)
      val crimeFuture2 = client.crimesNearby(1178459,1886515, max = 1)
      val result1: List[Crime] = Await.result(crimeFuture1, timeLimit)
      val result2: List[Crime] = Await.result(crimeFuture2, timeLimit)
      assert(result1.length == 5)
      assert(result2.length == 1)
    }
    it("Should be able to set an offset") {
      val offset1Future = client.crimesNearby(1178459,1886515, offset = 1)
      val offset2Future = client.crimesNearby(1178459,1886515, offset = 2)
      val off1: List[Crime] = Await.result(offset1Future, timeLimit)
      val off2: List[Crime] = Await.result(offset2Future, timeLimit)
      assert(off1(1) == off2(0))
      assert(off1 != off2)
    }
    it("Should be able to sort on a field name") {
      val sortedFuture = client.crimesNearby(1178459,1886515, sort = "block")
      val sorted: List[Crime] = Await.result(sortedFuture, timeLimit)
      assert(sorted == sorted.sortBy(_.block))
    }
  }

  describe("Crimes Record Division Number Method") {
    it("should return a list of Crime") {
      val crimeFuture = client.crimesRdNo(814)
      val result: List[Crime] = Await.result(crimeFuture, timeLimit)
      assert(!result.isEmpty)
    }
    it("should be able to set a max limit") {
      val crimeFuture1 = client.crimesRdNo(814, max = 5)
      val crimeFuture2 = client.crimesRdNo(814, max = 1)
      val result1: List[Crime] = Await.result(crimeFuture1, timeLimit)
      val result2: List[Crime] = Await.result(crimeFuture2, timeLimit)
      assert(result1.length == 5)
      assert(result2.length == 1)
    }
    it("Should be able to set an offset") {
      val offset1Future = client.crimesRdNo(814, offset = 1)
      val offset2Future = client.crimesRdNo(814, offset = 2)
      val off1: List[Crime] = Await.result(offset1Future, timeLimit)
      val off2: List[Crime] = Await.result(offset2Future, timeLimit)
      assert(off1(1) == off2(0))
      assert(off1 != off2)
    }
    it("Should be able to sort on a field name") {
      val sortedFuture = client.crimesRdNo(814, sort = "block")
      val sorted: List[Crime] = Await.result(sortedFuture, timeLimit)
      assert(sorted == sorted.sortBy(_.block))
    }
  }

  describe("Crimes Type Method") {
    it("should return a list of Crime") {
      val crimeFuture = client.crimesType("ASSAULT")
      val result: List[Crime] = Await.result(crimeFuture, timeLimit)
      assert(!result.isEmpty)
    }
    it("should be able to set a max limit") {
      val crimeFuture1 = client.crimesType("ASSAULT", max = 5)
      val crimeFuture2 = client.crimesType("ASSAULT", max = 1)
      val result1: List[Crime] = Await.result(crimeFuture1, timeLimit)
      val result2: List[Crime] = Await.result(crimeFuture2, timeLimit)
      assert(result1.length == 5)
      assert(result2.length == 1)
    }
    it("Should be able to set an offset") {
      val offset1Future = client.crimesType("ASSAULT", offset = 1)
      val offset2Future = client.crimesType("ASSAULT", offset = 2)
      val off1: List[Crime] = Await.result(offset1Future, timeLimit)
      val off2: List[Crime] = Await.result(offset2Future, timeLimit)
      assert(off1(1) == off2(0))
      assert(off1 != off2)
    }
    it("Should be able to sort on a field name") {
      val sortedFuture = client.crimesType("ASSAULT", sort = "block")
      val sorted: List[Crime] = Await.result(sortedFuture, timeLimit)
      assert(sorted == sorted.sortBy(_.block))
    }
  }

  describe("Crimes List Method") {
    it("should return a list of Crime") {
      val crimeFuture = client.crimesList()
      val result: List[Crime] = Await.result(crimeFuture, timeLimit)
      assert(!result.isEmpty)
    }
    it("should be able to set a max limit") {
      val crimeFuture1 = client.crimesList(max = 5)
      val crimeFuture2 = client.crimesList(max = 1)
      val result1: List[Crime] = Await.result(crimeFuture1, timeLimit)
      val result2: List[Crime] = Await.result(crimeFuture2, timeLimit)
      assert(result1.length == 5)
      assert(result2.length == 1)
    }
    it("Should be able to set an offset") {
      val offset1Future = client.crimesList(offset = 1)
      val offset2Future = client.crimesList(offset = 2)
      val off1: List[Crime] = Await.result(offset1Future, timeLimit)
      val off2: List[Crime] = Await.result(offset2Future, timeLimit)
      assert(off1(1) == off2(0))
      assert(off1 != off2)
    }
    it("Should be able to sort on a field name") {
      val sortedFuture = client.crimesList(sort = "block")
      val sorted: List[Crime] = Await.result(sortedFuture, timeLimit)
      assert(sorted == sorted.sortBy(_.block))
    }
  }

  describe("Mugshot Method") {
    it("should return a list of Mugshot") {
      val mugshotFuture = client.mugshots("CR007353")
      val result: List[Mugshot] = Await.result(mugshotFuture, timeLimit)
      assert(!result.isEmpty)
    }
  }
}
