package monad

import cats.free.Free
import cats.free.Free.liftF
import domain.Address

sealed trait AddressBookOp[A]
case class Put(address: Address) extends AddressBookOp[Unit]
case class Get(id : Long) extends AddressBookOp[Option[Address]]
case class Delete(address : Address) extends AddressBookOp[Unit]
case class Filter(foo : Address => Boolean) extends AddressBookOp[List[Address]]

object FreeAddressBookOps {

  type AddressBookOpF[A] = Free[AddressBookOp, A]

  def put(address: Address) : AddressBookOpF[Unit] = liftF[AddressBookOp, Unit](Put(address))
  def get(id: Long) : AddressBookOpF[Option[Address]] = liftF[AddressBookOp, Option[Address]](Get(id))
  def delete(address: Address) : AddressBookOpF[Unit] = liftF[AddressBookOp, Unit](Delete(address))
  def filter(foo: Address => Boolean) : AddressBookOpF[List[Address]] = liftF[AddressBookOp, List[Address]](Filter(foo))

}
