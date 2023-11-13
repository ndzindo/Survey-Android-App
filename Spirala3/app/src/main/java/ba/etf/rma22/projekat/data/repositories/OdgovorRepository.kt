package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.OdgovorBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object OdgovorRepository {

    suspend fun getOdgovoriAnketa(idAnkete: Int): List<Odgovor> {

        val poceteAnkete = TakeAnketaRepository.getPoceteAnkete() ?: return emptyList()

        val anketa =
            poceteAnkete.firstOrNull { anketaTaken -> anketaTaken.AnketumId == idAnkete }
                ?: return emptyList()


        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.getApiService()
                .getOdgovoriForAnketaAndStudent(AccountRepository.acHash, anketa.id)
            if (response.isSuccessful) {
                val odgovori = response.body()
                if (odgovori != null && odgovori.isNotEmpty()) {
                    return@withContext odgovori
                } else {
                    return@withContext emptyList()
                }
            } else {
                return@withContext emptyList()
            }
        }
    }

    suspend fun postaviOdgovorAnketa(idAnketaTaken: Int, idPitanje: Int, odgovor: Int): Int {
        // provjeriti je li anketa zapoceta
        val poceteAnkete = TakeAnketaRepository.getPoceteAnkete()

        if (poceteAnkete == null || poceteAnkete.isEmpty()) return -1

        try {
            var trenutnaAnketa: AnketaTaken? =
                poceteAnkete.filter { anketaTaken -> anketaTaken.id == idAnketaTaken }.firstOrNull()
                    ?: return -1

            val svaPitanja = PitanjeAnketaRepository.getPitanja(trenutnaAnketa!!.AnketumId)
            if (svaPitanja.isEmpty()) return -1

            val odgovorenaPitanja = getOdgovoriAnketa(idAnketaTaken)

            val progres =
                getProgressRounded((odgovorenaPitanja.size + 1).toDouble() / svaPitanja.size.toDouble())

            if (progres > 100) return -1

            val odgovorBody = OdgovorBody(odgovor, idPitanje, progres)


            return withContext(Dispatchers.IO) {
                val response =
                    ApiAdapter.getApiService().postOdgovoriForAnketaAndStudent(
                        AccountRepository.acHash,
                        idAnketaTaken,
                        odgovorBody
                    )
                if (response.body() != null) {
                    if (response.body()!!.odgovoreno == null) return@withContext -1
                    return@withContext progres
                } else return@withContext -1
            }
        } catch (e: Exception) {
            return -1
        }

    }

    private fun getProgressRounded(progress: Double): Int {
        return when (progress) {
            in 0.000000001..0.19999999999999 -> 20
            in 0.2..0.39999999999999 -> 40
            in 0.4..0.59999999999999 -> 60
            in 0.6..0.79999999999999 -> 80
            in 0.8..1.0 -> 100
            else -> {
                0
            }
        }
    }

}