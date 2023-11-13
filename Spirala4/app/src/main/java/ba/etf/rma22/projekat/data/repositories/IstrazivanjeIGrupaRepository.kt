package ba.etf.rma22.projekat.data.repositories

import android.util.Log
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.util.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object IstrazivanjeIGrupaRepository {

    suspend fun getIstrazivanja(offset: Int): List<Istrazivanje> {
        return withContext(Dispatchers.IO) {
            if (NetworkUtil.isDeviceConnected) {
                val response = ApiAdapter.getApiService().getAllIstrazivanja(offset)
                if (response.isSuccessful) {
                    AppDatabase.getInstance().istrazivanjeDao().insertAllEntities(response.body()!!)
                    return@withContext response.body()!!
                } else {
                    return@withContext emptyList()
                }
            } else {
                return@withContext AppDatabase.getInstance().istrazivanjeDao().getNthFive((offset - 1) * 5)
            }
        }
    }

    suspend fun getIstrazivanja(): List<Istrazivanje> {
        var offset = 1
        var collectedListIstrazivanja = mutableListOf<Istrazivanje>()

        if (NetworkUtil.isDeviceConnected) {
            while (true) {
                val paginatedList = getIstrazivanja(offset)

                collectedListIstrazivanja.addAll(paginatedList)

                if (paginatedList.size < 5) {
                    break
                } else {
                    offset++
                }
            }
        } else {
            try {
                return AppDatabase.getInstance().istrazivanjeDao().getAll()
            } catch (e: Exception) {
                collectedListIstrazivanja
            }
        }
        return collectedListIstrazivanja
    }

    suspend fun getGrupe(): List<Grupa> {

        return withContext(Dispatchers.IO) {
            if (NetworkUtil.isDeviceConnected) {
                val response = ApiAdapter.getApiService().getAllGrupe()
                if (response.isSuccessful) {
                    AppDatabase.getInstance().grupaDao().insertAllEntities(response.body()!!)
                    return@withContext response.body()!!
                } else {
                    return@withContext emptyList()
                }
            } else {
                try {
                    return@withContext AppDatabase.getInstance().grupaDao().getAll()
                } catch (e: Exception) {
                    return@withContext emptyList()
                }
            }
        }
    }

    suspend fun getUpisaneGrupe(): List<Grupa> {
        return withContext(Dispatchers.IO) {
            if (NetworkUtil.isDeviceConnected) {
                try {
                        val response = ApiAdapter.getApiService().getUpisaneGrupeByHashId(AccountRepository.getHash()!!)
                    if (response.isSuccessful) {
                        val upisaneGrupe = response.body()!!.toMutableList()
                        upisaneGrupe.forEach { grupa -> grupa.upisana = true }
                        AppDatabase.getInstance().grupaDao().insertAllEntities(upisaneGrupe)
                        upisaneGrupe.forEach { grupa -> grupa.upisana = false }
                        return@withContext response.body()!!
                    } else {
                        return@withContext emptyList()
                    }
                } catch (e: Exception) {
                    Log.e("RMA22", "Nema hasha")
                    return@withContext emptyList()
                }
            } else {
                return@withContext try {
                    AppDatabase.getInstance().grupaDao().getUpisane()
                } catch (e: Exception) {
                    return@withContext emptyList()
                }
            }
        }
    }

    suspend fun upisiUGrupu(idGrupa: Int) : Boolean{

        if (NetworkUtil.isDeviceConnected) {
            return withContext(Dispatchers.IO) {
                try {
                    val response = ApiAdapter.getApiService().addStudentWithHashIdToAGroupWithId(idGrupa, AccountRepository.getHash()!!)
                    return@withContext response.body()!!.message.contains("je dodan u grupu")
                } catch (e: Exception) {
                    Log.e("RMA22", "Nema hasha")
                    return@withContext false
                }
            }
        } else {
            return false
        }
    }

    suspend fun getGrupeZaIstrazivanje(idIstrazivanja: Int): List<Grupa> {
        return withContext(Dispatchers.IO) {
            if (NetworkUtil.isDeviceConnected) {
                val sveGrupe = getGrupe()
                return@withContext sveGrupe.filter { grupa ->
                    grupa.istrazivanjeId?.equals(idIstrazivanja) == true
                }
            } else {
                try {
                    val allGrupe = AppDatabase.getInstance().grupaDao().getAll()
                    return@withContext allGrupe.filter { grupa ->
                        grupa.istrazivanjeId?.equals(idIstrazivanja) == true
                    }
                } catch (e: Exception) {
                    return@withContext emptyList()
                }
            }
        }
    }
}
