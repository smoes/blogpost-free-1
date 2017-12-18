package database

import domain.Address
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object AddressBookDatabase {
  def put(address : Address) : Future[Either[Throwable, Unit]] = ???
  def get(id : Long) : Future[Either[Throwable, Option[Address]]] = ???
  def delete(address : Address) : Future[Either[Throwable, Unit]] = ???
  def filter(foo : Address => Boolean) : Future[Either[Throwable, List[Address]]] = ???

  def removeBielefeld : Future[Either[Throwable, List[Address]]] = {
    val bielefeldFE = filter(_.town == "Bielefeld")
    for {
      addressesE <-bielefeldFE
    } yield {
      addressesE.map(_.foreach(address => delete(address)))
      addressesE
    }
  }
}

