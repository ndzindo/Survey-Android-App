package ba.etf.rma22.projekat.data.repositories

import junit.framework.TestCase

class AnketaRepositoryUnitTest : TestCase() {

    fun testGetMyAnkete() {
        val mojeAnkete = AnketaRepository.getMyAnkete()

        assertEquals(7, mojeAnkete.size)
    }

    fun testGetAll() {
        val sveAnkete = AnketaRepository.getAll()

        assertEquals(15, sveAnkete.size)
    }

    fun testGetDone() {
        val doneAnkete = AnketaRepository.getDone()

        assertEquals(4, doneAnkete.size)
    }

    fun testGetFuture() {
        val futureAnkete = AnketaRepository.getFuture()

        assertEquals(1, futureAnkete.size)
    }

    fun testGetNotTaken() {
        val notTakenAnkete = AnketaRepository.getNotTaken()

        assertEquals(2, notTakenAnkete.size)
    }
}