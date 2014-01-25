clearpath-scala-client
======================

An asynchronous Scala client for accessing the Clearpath REST API, built using Spray and Akka.

In order to make use of the code you must import the akka ActorSystem, and the clearpath client:

```scala
import akka.actor.ActorSystem
import clearpath.ClearpathClient
```

Then, within the application, instantiate the actorsystem and client, and import the dispatcher:

```scala
implicit val system = ActorSystem("clearpath")
import system.dispatcher
val client = new ClearpathClient
```

Now that the client has been instatiated, you can use it to make API calls, which return Future[List[T]] where T is a Case Class that represent the JSON data. Also note that the fields are all wrapped in an Option type to account for missing data, and therefore you will need to access the data through the .get or .getOrElse methods.

```scala
import scala.util.{Success,Failure}
import clearpath.json._

val wantedFuture = client.mostWanted()

wantedFuture.onComplete { 
  case Success(criminals: List [WantedCriminal]) =>
    // process the list of wanted criminals
    criminals.foreach { criminal =>
      val name = criminal.offenderName.getOrElse("UNKNOWN")
      println(name)
    }
  case Failure(error) =>
    // handle exceptions
    println("An error occured.")
    println(error)
}
```

----------------------------

Currently the following methods are supported:

* mostWanted
 * Optional parameters - effectiveDate: String, district: String, max: Int, sort: String, offset: String
 * Return type - Future[List[WantedCriminal]]
* communityEvents
 * Optional parameters - max: Int, sort: String, offset: Int
 * Return type - Future[List[CommunityEvent]]
* communityCalendars
 * Optional parameters - max: Int, sort: String, offset: Int
 * Return type - Future[List[CommunityCalendar]]
* crimesMajor
 * Optional parameters - block: String, max: Int, sort: String, offset: Int
 * Return type - Future[List[Crime]]
* crimesNearby
 * Required parameters - x: Int, y: Int
 * Optional parameters - radius: Int, max: Int, sort: String, offset: Int
 * Return type - Future[List[Crime]]
* crimesRdNo
 * Required parameters - rdNo: Int
 * Optional parameters - max: Int, sort: String, offset: Int
 * Return type - Future[List[Crime]]
* crimesType
 * Required parameters - primary: String
 * Optional parameters - max: Int, sort: String, offset: Int
 * Return type - Future[List[Crime]]
* crimesList
 * Optional parameters - max: Int, sort: String, offset: Int
 * Return type - Future[List[Crime]]
* mugshots
 * Required parameters - warrantNo: String
 * Return type - Future[List[Mugshot]]

----------------------------

For more information on using the clearpath API, refer to the offical documentation:
http://api1.chicagopolice.org/clearpath/documentation
