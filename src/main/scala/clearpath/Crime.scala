package clearpath

import spray.json._

object CrimeJsonProtocol extends DefaultJsonProtocol {
  implicit val wantedFormat   = jsonFormat15(WantedCriminal)
  implicit val eventFormat    = jsonFormat9(CommunityEvent)
  implicit val calendarFormat = jsonFormat3(CommunityCalendar)
  implicit val mugshotFormat  = jsonFormat2(Mugshot)
  implicit val crimeFormat    = jsonFormat14(Crime)
}

case class WantedCriminal    (offenderName:    Option[String],
                              nickName:        Option[String],
                              sex:             Option[String],
                              race:            Option[String],
                              birthDate:       Option[String],
                              age:             Option[Int],
                              height:          Option[Int],
                              weight:          Option[Int],
                              build:           Option[String],
                              complexion:      Option[String],
                              eyeColor:        Option[String],
                              hairColor:       Option[String],
                              description:     Option[String],
                              effectiveDate:   Option[String],
                              warrantNo:       Option[String])

case class CommunityEvent    (calendarId:      Option[Int],
                              eventStartDate:  Option[String],
                              eventEndDate:    Option[String],
                              eventName:       Option[String],
                              eventDetails:    Option[String],
                              eventURL:        Option[String],
                              contactDetails:  Option[String],
                              location:        Option[String],
                              modifiedDate:    Option[String])

case class CommunityCalendar (calendarId:      Option[Int],
                              name:            Option[String],
                              description:     Option[String])

case class Mugshot           (mugshotNo:       Option[Int],
                              image:           Option[String])

case class Crime             (beat:            Option[String],
                              block:           Option[String],
                              rdNo:            Option[String],
                              communityArea:   Option[String],
                              dateOccurred:    Option[String],
                              iucrDescription: Option[String],
                              cpdDistrict:     Option[String],
                              iucr:            Option[String],
                              lastUpdated:     Option[String],
                              locationDesc:    Option[String],
                              primary:         Option[String],
                              ward:            Option[String],
                              xCoordinate:     Option[Int],
                              yCoordinate:     Option[Int])



