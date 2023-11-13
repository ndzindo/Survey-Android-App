package ba.etf.rma22.projekat.data.repositories

import junit.framework.TestCase

class GrupaRepositoryUnitTest : TestCase() {

    fun testGetGroupsByIstrazivanjet() {
        val grupe = GrupaRepository.getGroupsByIstrazivanje("nazivistrazivanja1")
        assertEquals(4, grupe.size)
    }
}