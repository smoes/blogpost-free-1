package monad

import cats.instances.list._
import cats.syntax.traverse._
import domain.Address
import monad.FreeAddressBookOps._
import monad.StateInterpreter.AddressState
import org.scalatest._

class InterpretersTest extends FlatSpec with Matchers {

  "A state interpreter" should "interpret programs as intended" in {

    val santa = Address(1, "Santa Clause", "Santa Street", "Bielefeld", "77777", "Santa Inc")

    val program = for {
      _              <- put(santa)
      bielefeldSanta <- AddressBookPrograms.removeBielefeld
      _              <- bielefeldSanta.traverse(address => put(address.copy(town = "Northpole")))
    } yield bielefeldSanta

    val state: AddressState[List[Address]] = program.foldMap(StateInterpreter.interpet)

    val (storage, bielefeldSanta) = state.run(Map.empty).value

    bielefeldSanta.head shouldEqual santa
    val (_, newSanta) = storage.head
    newSanta.town shouldEqual "Northpole"
  }
}