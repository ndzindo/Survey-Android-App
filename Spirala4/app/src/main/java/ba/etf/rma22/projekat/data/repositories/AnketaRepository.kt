package ba.etf.rma22.projekat.data.repositories

import android.util.Log
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.util.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

object AnketaRepository {

    private val allAnketaList = mutableListOf(
//        Anketa(
//            0,
//            "anketa1",
//            "nazivistrazivanja1",
//            Date(1640991600000),
//            Date(1657731780000),
//            null,
//            10,
//            "grupa1",
//            0F
//        ),
//        Anketa(
//            0,
//            "anketa2",
//            "nazivistrazivanja1",
//            Date(1641060180000),
//            Date(1657731780000),
//            null,
//            10,
//            "grupa2",
//            0F
//        ),
//        Anketa(
//            0,
//            "anketa3",
//            "nazivistrazivanja1",
//            Date(1640991600000),
//            Date(1657731780000),
//            null,
//            10,
//            "grupa6",
//            0.0F
//        ),
//        Anketa(
//            0,
//            "anketa4",
//            "nazivistrazivanja1",
//            Date(1640991600000),
//            Date(1642201200000),
//            Date(1641769200000),
//            10,
//            "grupa10",
//            0.33F
//        ),
//        Anketa(
//            0,
//            "anketa5",
//            "nazivistrazivanja2",
//            Date(1640991600000),
//            Date(1642201200000),
//            Date(1641769200000),
//            10,
//            "grupa3",
//            1F
//        ),
//        Anketa(
//            0,
//            "anketa6",
//            "nazivistrazivanja3",
//            Date(1640991600000),
//            Date(1642201200000),
//            Date(1641769200000),
//            10,
//            "grupa4",
//            1F
//        ),
//        Anketa(
//            0,
//            "anketa7",
//            "nazivistrazivanja3",
//            Date(1640991600000),
//            Date(1642201200000),
//            Date(1641769200000),
//            10,
//            "grupa5",
//            1F
//        ),
//        Anketa(
//            0,
//            "anketa8",
//            "nazivistrazivanja4",
//            Date(1657472580000),
//            Date(1657731780000),
//            null,
//            10,
//            "grupa7",
//            0F
//        ),
//        Anketa(
//            0,
//            "anketa9",
//            "nazivistrazivanja4",
//            Date(1640991600000),
//            Date(1642201200000),
//            Date(1641769200000),
//            10,
//            "grupa7",
//            1F
//        ),
//        Anketa(
//            0,
//            "anketa10",
//            "nazivistrazivanja4",
//            Date(1640991600000),
//            Date(1642201200000),
//            Date(1641769200000),
//            10,
//            "grupa7",
//            0.6F
//        ),
//        Anketa(
//            0,
//            "anketa11",
//            "nazivistrazivanja4",
//            Date(1649091780000),
//            Date(1642201200000),
//            null,
//            10,
//            "grupa7",
//            0F
//        ),
//        Anketa(
//            0,
//            "anketa12",
//            "nazivistrazivanja5",
//            Date(1640991600000),
//            Date(1642201200000),
//            Date(1641769200000),
//            10,
//            "grupa8",
//            1F
//        ),
//        Anketa(
//            0,
//            "anketa13",
//            "nazivistrazivanja5",
//            Date(1640991600000),
//            Date(1642201200000),
//            Date(1641769200000),
//            10,
//            "grupa8",
//            1F
//        ),
//        Anketa(
//            0,
//            "anketa14",
//            "nazivistrazivanja2",
//            Date(1640991600000),
//            Date(1642201200000),
//            Date(1641769200000),
//            10,
//            "grupa11",
//            1F
//        ),
        Anketa(
            0,
            "anketa15",
            "nazivistrazivanja5",
            Date(1640991600000),
            Date(1642201200000),
            Date(1641769200000),
            10,
            "grupa9",
            false,
            1F
        )
    )

    fun getMyAnkete(): List<Anketa> {
        return allAnketaList.filter { anketa -> Korisnik.upisaneGrupe.contains(anketa.nazivGrupe) }
    }

    fun getDone(): List<Anketa> {
        return allAnketaList.filter { anketa ->
            anketa.datumPocetak < Date() && anketa.datumRada != null && Korisnik.upisaneGrupe.contains(
                anketa.nazivGrupe
            )
        }
    }

    fun getFuture(): List<Anketa> {
        return allAnketaList.filter { anketa ->
            anketa.datumPocetak > Date() && Korisnik.upisaneGrupe.contains(
                anketa.nazivGrupe
            )
        }
    }

    fun getNotTaken(): List<Anketa> {
        return allAnketaList.filter { anketa ->
            anketa.datumPocetak < Date() && anketa.datumRada == null && Korisnik.upisaneGrupe.contains(
                anketa.nazivGrupe
            )
        }
    }

    fun predajAnketu(anketa: Anketa, progress: Float, datum: Date?) {
        val anketaId = allAnketaList.indexOfFirst {
            it.naziv == anketa.naziv && it.nazivIstrazivanja == anketa.nazivIstrazivanja
        }

        val updatedAnketa = allAnketaList[anketaId].apply {
            if (datum != null) {
                datumRada = datum
            }
            progres = progress
        }

        allAnketaList[anketaId] = updatedAnketa
    }

    // spirala 3
    suspend fun getAll(offset: Int): List<Anketa> {
//
        return withContext(Dispatchers.IO) {
            if (NetworkUtil.isDeviceConnected) {
                val response = ApiAdapter.getApiService().getAllAnketa(offset)
                if (response.isSuccessful) {
                    AppDatabase.getInstance().anketaDao().insertAllEntities(response.body()!!)
                    return@withContext response.body()!!
                } else {
                    return@withContext emptyList()
                }
            } else {
                return@withContext AppDatabase.getInstance().anketaDao().getNthFive(offset * 5)
            }
        }
    }

    suspend fun getAll(): List<Anketa> {
        var offset = 1
        var collectedListAnketa = mutableListOf<Anketa>()

        if (NetworkUtil.isDeviceConnected) {
            while (true) {
                val paginatedList = getAll(offset)

                collectedListAnketa.addAll(paginatedList)

                if (paginatedList.size < 5) {
                    break
                } else {
                    offset++
                }
            }
        } else {
            AppDatabase.getInstance().anketaDao().getAll()
        }
        return collectedListAnketa
    }

    suspend fun getById(id: Int): Anketa? {

        return withContext(Dispatchers.IO) {
            if (NetworkUtil.isDeviceConnected) {
                val result = ApiAdapter.getApiService().getAnketaById(id)

                if (result.body()?.naziv != null) {
                    AppDatabase.getInstance().anketaDao().insertAll(result.body()!!)
                    return@withContext result.body()
                } else {
                    return@withContext null
                }
            } else {
                try {
                    return@withContext AppDatabase.getInstance().anketaDao().getById(id)
                } catch (e: Exception) {
                    return@withContext null
                }
            }
        }
    }

    suspend fun getUpisane(): List<Anketa> {
        if (NetworkUtil.isDeviceConnected) {
            val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
            var upisaneAnkete = mutableListOf<Anketa>()
            //get IstrazivanjeIGrupaRepository.getUpisaneGrupe
            upisaneGrupe.forEach { grupa ->
                val response = ApiAdapter.getApiService().getAnketeByGrupaId(grupa.id)
                if (response.body() != null) {
                    upisaneAnkete.addAll(response.body()!!)
                }
            }
            var anketeZaBazu = upisaneAnkete.toMutableList()
            anketeZaBazu.forEach { anketa -> anketa.upisana = true }
            AppDatabase.getInstance().anketaDao().insertAllEntities(anketeZaBazu)
            anketeZaBazu.forEach { anketa -> anketa.upisana = false }

            return upisaneAnkete
        } else {
            return try {
                AppDatabase.getInstance().anketaDao().getUpisane()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

}