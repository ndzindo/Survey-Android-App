package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa


object GrupaRepository {

    private val allGrupaList: List<Grupa> = listOf(
        Grupa(0,"grupa1", "nazivistrazivanja1"),
        Grupa(0,"grupa2", "nazivistrazivanja1"),
        Grupa(0,"grupa3", "nazivistrazivanja2"),
        Grupa(0,"grupa4", "nazivistrazivanja3"),
        Grupa(0,"grupa5", "nazivistrazivanja3"),
        Grupa(0,"grupa6", "nazivistrazivanja1"),
        Grupa(0,"grupa7", "nazivistrazivanja4"),
        Grupa(0,"grupa8", "nazivistrazivanja5"),
        Grupa(0,"grupa9", "nazivistrazivanja5"),
        Grupa(0,"grupa10", "nazivistrazivanja1"),
        Grupa(0,"grupa11", "nazivistrazivanja2"),
    )

    fun getGroupsByIstrazivanje(nazivIstrazivanja: String): List<Grupa> {
        return allGrupaList.filter { grupa -> grupa.nazivIstrazivanja == nazivIstrazivanja }
    }
}