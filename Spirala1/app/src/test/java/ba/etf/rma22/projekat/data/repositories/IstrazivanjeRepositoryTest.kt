package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Korisnik
import junit.framework.TestCase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.hasProperty
import org.hamcrest.CoreMatchers.`is` as Is

class IstrazivanjeRepositoryTest : TestCase() {

    fun testGetIstrazivanjeByGodina() {
        val godina = 1
        val istrazivanja = IstrazivanjeRepository.getIstrazivanjeByGodina(godina)
        assertEquals(1, istrazivanja.size)
        assertThat(istrazivanja, hasItem<Istrazivanje>(hasProperty("naziv", Is("nazivistrazivanja1"))))
    }

    fun testGetAll() {
        val istrazivanja = IstrazivanjeRepository.getAll()
        assertEquals(5, istrazivanja.size)
    }

    fun testGetUpisani() {
        val brojUpisanih = Korisnik.upisanaIstrazivanja.size
        val istrazivanja = IstrazivanjeRepository.getUpisani()

        assertEquals(brojUpisanih, istrazivanja.size)
    }
}