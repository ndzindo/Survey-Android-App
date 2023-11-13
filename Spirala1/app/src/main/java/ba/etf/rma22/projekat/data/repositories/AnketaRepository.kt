package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Korisnik
import java.util.*

object AnketaRepository {

    private val allAnketaList: List<Anketa> = listOf(
        Anketa("anketa1", "nazivistrazivanja1", Date(1640991600000), Date(1657731780000), null, 10, "grupa1", 0F),
        Anketa("anketa2", "nazivistrazivanja1", Date(1641060180000), Date(1641060180001), null, 10, "grupa2", 0F),
        Anketa("anketa3", "nazivistrazivanja1", Date(1640991600000), Date(1642201200000), Date(1641769200000), 10, "grupa6", 0.1F),
        Anketa("anketa4", "nazivistrazivanja1", Date(1640991600000), Date(1642201200000), Date(1641769200000), 10, "grupa10", 0.33F),
        Anketa("anketa5", "nazivistrazivanja2", Date(1640991600000), Date(1642201200000), Date(1641769200000), 10, "grupa3", 1F),
        Anketa("anketa6", "nazivistrazivanja3", Date(1640991600000), Date(1642201200000), Date(1641769200000), 10, "grupa4", 1F),
        Anketa("anketa7", "nazivistrazivanja3", Date(1640991600000), Date(1642201200000), Date(1641769200000), 10, "grupa5", 1F),
        Anketa("anketa8", "nazivistrazivanja4", Date(1657472580000), Date(1657731780000), null, 10, "grupa7", 0F),
        Anketa("anketa9", "nazivistrazivanja4", Date(1640991600000), Date(1642201200000), Date(1641769200000), 10, "grupa7", 1F),
        Anketa("anketa10", "nazivistrazivanja4", Date(1640991600000), Date(1642201200000), Date(1641769200000), 10, "grupa7", 0.6F),
        Anketa("anketa11", "nazivistrazivanja4", Date(1649091780000), Date(1657731780000), null, 10, "grupa7", 0F),
        Anketa("anketa12", "nazivistrazivanja5", Date(1640991600000), Date(1642201200000), Date(1641769200000), 10, "grupa8", 1F),
        Anketa("anketa13", "nazivistrazivanja5", Date(1640991600000), Date(1642201200000), Date(1641769200000), 10, "grupa8", 1F),
        Anketa("anketa14", "nazivistrazivanja2", Date(1640991600000), Date(1642201200000), Date(1641769200000), 10, "grupa11", 1F),
        Anketa("anketa15", "nazivistrazivanja5", Date(1640991600000), Date(1642201200000), Date(1641769200000), 10, "grupa9", 1F)
    )

    fun getMyAnkete(): List<Anketa> {
        return allAnketaList.filter { anketa -> Korisnik.upisaneGrupe.contains(anketa.nazivGrupe) }
    }

    fun getAll(): List<Anketa> {
        return allAnketaList
    }

    fun getDone(): List<Anketa> {
        return allAnketaList.filter { anketa -> anketa.datumPocetak < Date() && anketa.datumRada != null && Korisnik.upisaneGrupe.contains(anketa.nazivGrupe)}
    }

    fun getFuture(): List<Anketa> {
        return allAnketaList.filter { anketa -> anketa.datumPocetak > Date() && Korisnik.upisaneGrupe.contains(anketa.nazivGrupe)}
    }

    fun getNotTaken(): List<Anketa> {
        return allAnketaList.filter { anketa -> anketa.datumPocetak < Date() && anketa.datumRada == null && Korisnik.upisaneGrupe.contains(anketa.nazivGrupe)}
    }

}