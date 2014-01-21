package clearpath

import scala.concurrent.Await
import scala.concurrent.duration._
import akka.actor.ActorSystem

import org.scalatest.FunSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ClientTest extends FunSpec {

  implicit val system = ActorSystem("testing-clearpath-client")
  val client = new ClearpathClient

  describe("Most Wanted Method"){
    it("should return a list of WantedCriminal") {
      val wantedFuture = client.mostWanted()
      val result: List[WantedCriminal] = Await.result(wantedFuture, 5 seconds)
      assert(!result.isEmpty)
    }
    it("should be able to set a max limit") {
      val wantedFuture1 = client.mostWanted(max = 5)
      val result1: List[WantedCriminal] = Await.result(wantedFuture1, 5 seconds)
      val wantedFuture2 = client.mostWanted(max = 1)
      val result2: List[WantedCriminal] = Await.result(wantedFuture2, 5 seconds)
      assert(result1.length <= 5)
      assert(result2.length == 1)
    }
  }

}
