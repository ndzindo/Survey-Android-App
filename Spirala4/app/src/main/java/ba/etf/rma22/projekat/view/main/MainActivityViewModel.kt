package ba.etf.rma22.projekat.view.main

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.data.repositories.AccountRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivityViewModel {

    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getPitanjaForNazivAnketaAndNazivIstrazivanje(
        nazivAnkete: String,
        nazivIstrazivanja: String
    ) = PitanjeAnketaRepository.getPitanja(nazivAnkete, nazivIstrazivanja)

    fun korisnikImaPristup(anketa: Anketa): Boolean {
        return Korisnik.upisaneGrupe.contains(anketa.nazivGrupe) && Korisnik.upisanaIstrazivanja.contains(anketa.nazivIstrazivanja)
    }

    fun saveKorisnikHash(payload: String) {
        // sacuvat ce korisnika
        scope.launch {
            AccountRepository.postaviHash(payload)
        }
    }
}