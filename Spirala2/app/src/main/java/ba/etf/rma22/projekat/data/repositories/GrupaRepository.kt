package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa


object GrupaRepository {

    private val allGrupaList: List<Grupa> = listOf(
        Grupa("grupa1", "nazivistrazivanja1"),
        Grupa("grupa2", "nazivistrazivanja1"),
        Grupa("grupa3", "nazivistrazivanja2"),
        Grupa("grupa4", "nazivistrazivanja3"),
        Grupa("grupa5", "nazivistrazivanja3"),
        Grupa("grupa6", "nazivistrazivanja1"),
        Grupa("grupa7", "nazivistrazivanja4"),
        Grupa("grupa8", "nazivistrazivanja5"),
        Grupa("grupa9", "nazivistrazivanja5"),
        Grupa("grupa10", "nazivistrazivanja1"),
        Grupa("grupa11", "nazivistrazivanja2"),
    )

    fun getGroupsByIstrazivanje(nazivIstrazivanja: String): List<Grupa> {
        return allGrupaList.filter { grupa -> grupa.nazivIstrazivanja == nazivIstrazivanja }
    }
}