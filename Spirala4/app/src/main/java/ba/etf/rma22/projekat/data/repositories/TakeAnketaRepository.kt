package ba.etf.rma22.projekat.data.repositories

import android.util.Log
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.util.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TakeAnketaRepository {

    suspend fun zapocniAnketu(idAnkete: Int): AnketaTaken? {
        if (NetworkUtil.isDeviceConnected) {
            val poceteAnkete = getPoceteAnkete()

            if (poceteAnkete != null) {
                if (poceteAnkete.isEmpty()) {
                    return null
                }

                val filter: List<AnketaTaken> =
                    poceteAnkete!!.filter { pocetaAnketa -> pocetaAnketa.AnketumId == idAnkete }

                if (filter.isNotEmpty()) return null
            }

            try {
                val postZapocniPokusaj =
                    ApiAdapter.getApiService()
                        .postZapocniPokusaj(AccountRepository.getHash()!!, idAnkete)


                if (postZapocniPokusaj.id == null || postZapocniPokusaj.student == null) return null

                AppDatabase.getInstance().anketaTakenDao().insertAll(postZapocniPokusaj)
                return postZapocniPokusaj

            } catch (e: Exception) {
                Log.e("RMA22", "Nema hasha")
                return null
            }
        } else {
            return null
        }
    }

    suspend fun getPoceteAnkete(): List<AnketaTaken>? {
        return withContext(Dispatchers.IO) {
            if (NetworkUtil.isDeviceConnected) {
               try {
                   val response =
                       ApiAdapter.getApiService().getPokusajiByHash(AccountRepository.getHash()!!)
                   if (response.body() != null && response.body()!!.isNotEmpty()) {
                       val poceteAnkete = response.body()!!
                       AppDatabase.getInstance().anketaTakenDao().insertAllEntities(poceteAnkete)
                       return@withContext response.body()!!
                   } else {
                       return@withContext null
                   }
               } catch (e: Exception) {
                   return@withContext null
               }
            } else {
                return@withContext try {
                    val all = AppDatabase.getInstance().anketaTakenDao().getAll()
                    all.ifEmpty {
                        null
                    }
                } catch (e: Exception) {
                    null
                }
            }
        }
    }
}