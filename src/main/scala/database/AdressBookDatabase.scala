package database

import domain.Address

import scala.concurrent.Future

object AddressBookDatabase {
  def put(address : Address) : Future[Either[Throwable, Unit]] = ???
  def get(id : Long) : Future[Either[Throwable, Option[Address]]] = ???
  def delete(id : Address) : Future[Either[Throwable, Unit]] = ???
  def filter(foo : Address => Boolean) : Future[Either[Throwable, List[Address]]] = ???
}

