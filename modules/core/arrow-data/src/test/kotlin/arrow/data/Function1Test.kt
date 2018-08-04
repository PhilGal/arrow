package arrow.data

import arrow.Kind
import arrow.core.*
import arrow.instances.ForFunction1
import arrow.test.UnitSpec
import arrow.test.laws.ContravariantLaws
import arrow.test.laws.MonadLaws
import arrow.test.laws.ProfunctorLaws
import arrow.typeclasses.Conested
import arrow.typeclasses.Eq
import arrow.typeclasses.conest
import arrow.typeclasses.counnest
import io.kotlintest.KTestJUnitRunner
import org.junit.runner.RunWith

@RunWith(KTestJUnitRunner::class)
class Function1Test : UnitSpec() {
  val ConestedEQ: Eq<Kind<Conested<ForFunction1, Int>, Int>> = Eq { a, b ->
    a.counnest().invoke(1) == b.counnest().invoke(1)
  }

  val EQ: Eq<Function1Of<Int, Int>> = Eq { a, b ->
    a(1) == b(1)
  }

  init {
    ForFunction1<Int>() extensions {
      testLaws(
        ContravariantLaws.laws(Function1.contravariant(), { Function1.just<Int, Int>(it).conest() }, ConestedEQ),
        ProfunctorLaws.laws(Function1.profunctor(), { Function1.just(it) }, EQ),
        MonadLaws.laws(this, EQ)
      )
    }
  }
}
