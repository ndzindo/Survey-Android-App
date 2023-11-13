package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.AnketaTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TakeAnketaRepository {

    suspend fun zapocniAnketu(idAnkete: Int): AnketaTaken? {
        val poceteAnkete = getPoceteAnkete()

        if (poceteAnkete != null) {
            if (poceteAnkete.isEmpty()) { return null}

            val filter: List<AnketaTaken> =
                poceteAnkete!!.filter { pocetaAnketa -> pocetaAnketa.AnketumId == idAnkete }

            if (filter.isNotEmpty()) return null
        }

        val postZapocniPokusaj =
            ApiAdapter.getApiService().postZapocniPokusaj(AccountRepository.acHash, idAnkete)

        if (postZapocniPokusaj.id == null || postZapocniPokusaj.student == null) return null
        return postZapocniPokusaj
    }

    suspend fun getPoceteAnkete(): List<AnketaTaken>? {
        return withContext(Dispatchers.IO) {
            val response =
                ApiAdapter.getApiService().getPokusajiByHash(AccountRepository.acHash)
            if (response.body() != null && response.body()!!.isNotEmpty()) {
                return@withContext response.body()!!
            } else {
                return@withContext null
            }
        }
    }
}