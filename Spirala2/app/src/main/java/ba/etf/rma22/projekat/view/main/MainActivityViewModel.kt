package ba.etf.rma22.projekat.view.main

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository

class MainActivityViewModel {

    fun getPitanjaForNazivAnketaAndNazivIstrazivanje(
        nazivAnkete: String,
        nazivIstrazivanja: String
    ) = PitanjeAnketaRepository.getPitanja(nazivAnkete, nazivIstrazivanja)

    fun korisnikImaPristup(anketa: Anketa): Boolean {
        return Korisnik.upisaneGrupe.contains(anketa.nazivGrupe) && Korisnik.upisanaIstrazivanja.contains(anketa.nazivIstrazivanja)
    }
}