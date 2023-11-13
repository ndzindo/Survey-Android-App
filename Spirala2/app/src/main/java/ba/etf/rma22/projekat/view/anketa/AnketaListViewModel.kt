package ba.etf.rma22.projekat.view.anketa

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository

class AnketaListViewModel {

    fun getMyAnkete(): List<Anketa> = AnketaRepository.getMyAnkete()


    fun getAll(): List<Anketa> = AnketaRepository.getAll()


    fun getDoneAnkete(): List<Anketa> = AnketaRepository.getDone()


    fun getFutureAnkete(): List<Anketa> = AnketaRepository.getFuture()

    fun getNotTakenAnkete(): List<Anketa> = AnketaRepository.getNotTaken()

}