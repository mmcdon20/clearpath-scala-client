package examples

import akka.actor.ActorSystem
import clearpath._
import spray.util._

object Example extends App {
  implicit val system = ActorSystem("clearpath")
  import system.{log,shutdown}

  val client = new ClearpathClient

  val wantedFuture    = client.mostWanted()
  val eventsFuture    = client.communityEvents()
  val calendarsFuture = client.communityCalendars()

  val default = "UNKNOWN"

  wantedFuture.onSuccess { case criminals =>
    criminals foreach { criminal =>
      val name = criminal.offenderName.getOrElse(default)
      val desc = criminal.description.getOrElse(default)
      log.info(s"WANTED: $name - $desc".replaceAll("\r\n"," "))
    }
  }

  eventsFuture.onSuccess { case events =>
    events foreach { event =>
      val name = event.eventName.getOrElse(default)
      val date = event.eventStartDate.getOrElse(default)
      log.info(s"EVENT: $name - $date")
    }
  }

  calendarsFuture.onSuccess { case calendars =>
    calendars foreach { calendar =>
      val name = calendar.name.getOrElse(default)
      val desc = calendar.description.getOrElse(default)
      log.info(s"CALENDAR: $name - $desc")
    }
  }

  Thread.sleep(2000) // waiting a few seconds before shutting down
  shutdown
}
