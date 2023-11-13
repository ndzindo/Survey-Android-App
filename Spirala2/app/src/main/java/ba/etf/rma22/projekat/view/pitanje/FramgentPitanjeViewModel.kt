package ba.etf.rma22.projekat.view.pitanje

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository

class FramgentPitanjeViewModel {

    fun getSacuvaniOdgovorZaPitanjeAnkete(nazivPitanje: String, nazivAnketa: String, nazivIstrazivanje: String) = PitanjeAnketaRepository.getSacuvaniOdgovorZaPitanje(nazivPitanje, nazivAnketa, nazivIstrazivanje)

    fun updateSacuvaniOdgovorZaPitanjeAnkete(naziv: String, nazivAnketa: String, nazivIstrazivanja: String, odabranaOpcija: Int) {
        PitanjeAnketaRepository.UpdateSacuvaniOdgovorZaPitanje(naziv, nazivAnketa, nazivIstrazivanja, odabranaOpcija)
    }

    fun updateProgresForAnketa(anketa: Anketa) {
        val progresForAnketa = PitanjeAnketaRepository.getProgresForAnketa(anketa)
        AnketaRepository.predajAnketu(anketa, progresForAnketa, null)
    }

}
