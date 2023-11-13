package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Korisnik

object IstrazivanjeRepository {

    private val allIstrazivanjeList: List<Istrazivanje> = listOf(
        Istrazivanje(0,"nazivistrazivanja1", 1),
        Istrazivanje(0,"nazivistrazivanja2", 2),
        Istrazivanje(0,"nazivistrazivanja3", 3),
        Istrazivanje(0,"nazivistrazivanja4", 3),
        Istrazivanje(0,"nazivistrazivanja5", 5)
    )

    fun getIstrazivanjeByGodina(godina: Int): List<Istrazivanje> {
        return allIstrazivanjeList.filter { istrazivanje -> istrazivanje.godina == godina }
    }

    fun getAll(): List<Istrazivanje> = allIstrazivanjeList

    fun getUpisani(): List<Istrazivanje> {
        return allIstrazivanjeList.filter { istrazivanje -> Korisnik.upisanaIstrazivanja.contains(istrazivanje.naziv) }
    }
}