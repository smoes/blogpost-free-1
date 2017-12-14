package monad

import cats.data.State
import cats.~>
import database.AddressBookDatabase
import domain.Address

import scala.concurrent.Future


object StateInterpreter {

  type AddressBook = Map[Long, Address]
  type AddressState[A] = State[AddressBook, A]

  val interpet = new (AddressBookOp ~> AddressState) {
    def apply[A](fa: AddressBookOp[A]): AddressState[A] = fa match {
      case Put(address) => State.modify[AddressBook] {_ + (address.id -> address)}
      case Get(id) => State.inspect[AddressBook, Option[Address]] {_.get(id)}
      case Delete(address) => State.modify[AddressBook] {_ - address.id}
      case Filter(foo) => State.inspect {_.values.filter(foo).toList}
    }
  }
}

object DatabaseInterpreter {

  type DatabaseReturnType[A] = Future[Either[Throwable, A]]

  val interpret = new (AddressBookOp ~> DatabaseReturnType) {
    def apply[A](fa: AddressBookOp[A]) : DatabaseReturnType[A] = fa match {
      case Put(address) => AddressBookDatabase.put(address)
      case Get(id) => AddressBookDatabase.get(id)
      case Delete(address) => AddressBookDatabase.delete(address)
      case Filter(foo) => AddressBookDatabase.filter(foo)
    }
  }
}