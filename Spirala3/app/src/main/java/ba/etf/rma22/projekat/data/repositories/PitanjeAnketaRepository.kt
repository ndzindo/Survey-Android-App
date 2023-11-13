package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import ba.etf.rma22.projekat.data.models.Pitanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PitanjeAnketaRepository {

    private val allPitanja: List<Pitanje> = mutableListOf(
    )

    private val ALL_PITANJE_ANKETA: List<PitanjeAnketa> = mutableListOf(
        PitanjeAnketa("a3i1p1", "anketa3", "nazivistrazivanja1", null),
        PitanjeAnketa("a3i1p2", "anketa3", "nazivistrazivanja1", null),
        PitanjeAnketa("a3i1p3", "anketa3", "nazivistrazivanja1", null),
        PitanjeAnketa("a3i1p4", "anketa3", "nazivistrazivanja1", null),

        PitanjeAnketa("a1i1p1", "anketa1", "nazivistrazivanja1", null),
        PitanjeAnketa("a2i1p1", "anketa2", "nazivistrazivanja1", null),
        PitanjeAnketa("a4i1p1", "anketa4", "nazivistrazivanja1", null),

        PitanjeAnketa("a5i2p1", "anketa5", "nazivistrazivanja2", null),
        PitanjeAnketa("a6i3p1", "anketa6", "nazivistrazivanja3", null),
        PitanjeAnketa("a7i3p1", "anketa7", "nazivistrazivanja3", null),
        PitanjeAnketa("a8i4p1", "anketa8", "nazivistrazivanja4", null),
        PitanjeAnketa("a9i4p1", "anketa9", "nazivistrazivanja4", null),
        PitanjeAnketa("a10i4p1", "anketa10", "nazivistrazivanja4", null),
        PitanjeAnketa("a11i4p1", "anketa11", "nazivistrazivanja4", null),
        PitanjeAnketa("a12i5p1", "anketa12", "nazivistrazivanja5", null),
        PitanjeAnketa("a13i5p1", "anketa13", "nazivistrazivanja5", null),
        PitanjeAnketa("a14i2p1", "anketa14", "nazivistrazivanja2", null),
        PitanjeAnketa("a15i5p1", "anketa15", "nazivistrazivanja5", null)
    )

    fun getPitanja(nazivAnkete: String, nazivIstrazivanja: String): List<Pitanje> {

        val naziviPitanja = ALL_PITANJE_ANKETA.filter { pitanjaAnketa ->
            pitanjaAnketa.anketa == nazivAnkete && pitanjaAnketa.istrazivanje == nazivIstrazivanja }.map { pitanjaAnketa -> pitanjaAnketa.naziv }
        return allPitanja.filter { pitanje -> naziviPitanja.contains(pitanje.naziv) }
    }

    fun getSacuvaniOdgovorZaPitanje(
        nazivPitanje: String,
        nazivAnketa: String,
        nazivIstrazivanje: String
    ): Int? {
        return ALL_PITANJE_ANKETA.first { pitanjaAnketa ->
            pitanjaAnketa.naziv == nazivPitanje && pitanjaAnketa.anketa == nazivAnketa && pitanjaAnketa.istrazivanje == nazivIstrazivanje
        }.indexOdgovora
    }

    fun UpdateSacuvaniOdgovorZaPitanje(
        nazivPitanje: String,
        nazivAnketa: String,
        nazivIstrazivanje: String,
        odabranaOpcija: Int
    ) {
        ALL_PITANJE_ANKETA.find { pitanjaAnketa ->
            pitanjaAnketa.naziv == nazivPitanje && pitanjaAnketa.anketa == nazivAnketa && pitanjaAnketa.istrazivanje == nazivIstrazivanje
        }?.indexOdgovora = odabranaOpcija
    }

    fun getProgresForAnketa(anketa: Anketa): Float {
        val filter =
            ALL_PITANJE_ANKETA.filter { pitanjaAnketa -> pitanjaAnketa.anketa == anketa.naziv && pitanjaAnketa.istrazivanje == anketa.nazivIstrazivanja }
        val brojOdgovorenih = filter.count { pitanjaAnketa -> pitanjaAnketa.indexOdgovora != null }

        return (brojOdgovorenih.toDouble()/filter.size).toFloat()
    }

    suspend fun getPitanja(idAnkete: Int): List<Pitanje> {

        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.getApiService().getPitanjaByAnketaId(idAnkete)
            if (response.isSuccessful) {
                return@withContext response.body()!!
            } else {
                return@withContext emptyList()
            }
        }
    }
}