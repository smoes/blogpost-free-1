package monad

import cats.free.Free
import domain.Address
import monad.FreeAddressBookOps._
import cats.instances.list._
import cats.syntax.traverse._

object AddressBookPrograms {

    val removeBielefeld: Free[AddressBookOp, List[Address]] = for {
      addresses <- filter(_.town == "Bielefeld")
      _ <- addresses.traverse(delete(_))
    } yield addresses

    val fixTypo = for {
      addresses <- filter(_.town == "Londn")
      _ <- addresses.traverse(address => put(address.copy(town = "London")))
      _ <- addresses.traverse(delete(_))
    } yield ()

  }

