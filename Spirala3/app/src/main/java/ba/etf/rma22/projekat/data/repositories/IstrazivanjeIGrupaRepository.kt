package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object IstrazivanjeIGrupaRepository {

    suspend fun getIstrazivanja(offset: Int): List<Istrazivanje> {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.getApiService().getAllIstrazivanja(offset)
            if (response.isSuccessful) {
                return@withContext response.body()!!
            } else {
                return@withContext emptyList()
            }
        }
    }

    suspend fun getIstrazivanja(): List<Istrazivanje> {
        var offset = 1
        var collectedListIstrazivanja = mutableListOf<Istrazivanje>()

        while (true) {
            val paginatedList = getIstrazivanja(offset)

            collectedListIstrazivanja.addAll(paginatedList)

            if (paginatedList.size < 5) {
                break
            } else {
                offset++
            }
        }
        return collectedListIstrazivanja
    }

    suspend fun getGrupe(): List<Grupa> {

        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.getApiService().getAllGrupe()
            if (response.isSuccessful) {
                return@withContext response.body()!!
            } else {
                return@withContext emptyList()
            }
        }
    }

    suspend fun getUpisaneGrupe(): List<Grupa> {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.getApiService().getUpisaneGrupeByHashId(AccountRepository.acHash)
            if (response.isSuccessful) {
                return@withContext response.body()!!
            } else {
                return@withContext emptyList()
            }
        }
    }

    suspend fun upisiUGrupu(idGrupa: Int) : Boolean{

        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.getApiService().addStudentWithHashIdToAGroupWithId(idGrupa, AccountRepository.acHash)
            return@withContext response.body()!!.message.contains("je dodan u grupu")
        }
    }

    suspend fun getGrupeZaIstrazivanje(idIstrazivanja: Int): List<Grupa> {
        return withContext(Dispatchers.IO) {
            val sveGrupe = getGrupe()
            return@withContext sveGrupe.filter { grupa ->
                grupa.istrazivanjeId?.equals(idIstrazivanja) == true
            }
        }
    }
}
