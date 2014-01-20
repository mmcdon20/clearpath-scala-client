package examples

import akka.actor.ActorSystem
import clearpath._

object Example extends App {
  implicit val system = ActorSystem("clearpath")
  import system.{dispatcher,log,shutdown}
  val client = new ClearpathClient

  val wantedFuture       = client.mostWanted()
  //val eventsFuture       = client.communityEvents()
  //val calendarsFuture    = client.communityCalendars()
  //val majorCrimesFuture  = client.crimesMajor()
  //val nearbyCrimesFuture = client.crimesNearby(1178459,1886515)
  //val rdNoCrimesFuture   = client.crimesRdNo(814)
  //val typeCrimesFuture   = client.crimesType("ASSAULT")
  //val listCrimesFuture   = client.crimesList()
  //val mugshotsFuture    = client.mugshots("CR007353")

  val default = "UNKNOWN"


  wantedFuture.onSuccess { case criminals =>
    criminals foreach { criminal =>
      val name = criminal.offenderName.getOrElse(default)
      val desc = criminal.description.getOrElse(default)
      val warr = criminal.warrantNo.getOrElse(default)
      log.info(s"WANTED: $name - $desc ($warr)".replaceAll("\r\n"," "))
    }
  }
  /*
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

  majorCrimesFuture.onSuccess { case majorCrimes =>
    majorCrimes foreach { crime =>
      val kind = crime.primary.getOrElse(default)
      val desc = crime.iucrDescription.getOrElse(default)
      val x = crime.xCoordinate.getOrElse(default)
      val y = crime.yCoordinate.getOrElse(default)
      log.info(s"MAJOR CRIME: $kind - $desc ($x,$y)")
    }
  }

  nearbyCrimesFuture.onSuccess { case nearbyCrimes =>
    nearbyCrimes foreach { crime =>
      val kind = crime.primary.getOrElse(default)
      val desc = crime.iucrDescription.getOrElse(default)
      val x = crime.xCoordinate.getOrElse(default)
      val y = crime.yCoordinate.getOrElse(default)
      log.info(s"NEARBY CRIME: $kind - $desc ($x,$y)")
    }
  }

  rdNoCrimesFuture.onSuccess { case rdNoCrimes =>
    rdNoCrimes foreach { crime =>
      val kind = crime.primary.getOrElse(default)
      val desc = crime.iucrDescription.getOrElse(default)
      val x = crime.xCoordinate.getOrElse(default)
      val y = crime.yCoordinate.getOrElse(default)
      log.info(s"RDNO CRIME: $kind - $desc ($x,$y)")
    }
  }

  typeCrimesFuture.onSuccess { case typeCrimes =>
    typeCrimes foreach { crime =>
      val kind = crime.primary.getOrElse(default)
      val desc = crime.iucrDescription.getOrElse(default)
      val x = crime.xCoordinate.getOrElse(default)
      val y = crime.yCoordinate.getOrElse(default)
      log.info(s"TYPE CRIME: $kind - $desc ($x,$y)")
    }
  }

  listCrimesFuture.onSuccess { case listCrimes =>
    println("SWAG")
    listCrimes foreach { crime =>
      //val kind = crime.primary.getOrElse(default)
      //val desc = crime.iucrDescription.getOrElse(default)
      //val x = crime.xCoordinate.getOrElse(default)
      //val y = crime.yCoordinate.getOrElse(default)
      println("SWAG")
      log.info(s"LIST CRIME:")
    }
  }

  listCrimesFuture.onFailure{ case failure => println(failure) }

  mugshotsFuture.onSuccess { case mugshots =>
    mugshots foreach { case mugshot =>
      val num = mugshot.mugshotNo.getOrElse(default)
      val img = mugshot.image.getOrElse(default)
      log.info(s"MUGSHOT: $num - ${img.toString.take(10)}")
    }
  }*/

  Thread.sleep(5000) // waiting a few seconds before shutting down
  shutdown
}
