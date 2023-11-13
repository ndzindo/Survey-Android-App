package ba.etf.rma22.projekat.view.pitanje

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import java.util.*

class FramgentPredajViewModel {

    fun getProgresForAnketa(anketa: Anketa): Float {
        return PitanjeAnketaRepository.getProgresForAnketa(anketa)
    }

    fun predajAnketu(anketa: Anketa, progress: Float) {
        AnketaRepository.predajAnketu(anketa, progress, Date())
    }

}
