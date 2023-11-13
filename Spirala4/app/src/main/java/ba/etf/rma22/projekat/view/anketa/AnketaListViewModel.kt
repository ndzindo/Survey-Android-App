package ba.etf.rma22.projekat.view.anketa

import android.util.Log
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AnketaListViewModel {

    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getMyAnkete(): List<Anketa> = AnketaRepository.getMyAnkete()


    fun getAll(
        onSuccess: (List<Anketa>) -> Unit,
        onError: (String) -> Unit
    ) {
        scope.launch {
            val result = AnketaRepository.getAll()
            if (result.isNotEmpty()) {
                onSuccess(result)
            } else {
                onError("Prazna lista")
            }


//        val result = AnketaRepository.getById(1323323223)
//            Log.e("TAG", (result==null).toString())
//
//            val result2 = AnketaRepository.getById(1)
//            Log.e("TAG", (result2==null).toString())
        }


    }

    fun getDoneAnkete(): List<Anketa> = AnketaRepository.getDone()


    fun getFutureAnkete(): List<Anketa> = AnketaRepository.getFuture()

    fun getNotTakenAnkete(): List<Anketa> = AnketaRepository.getNotTaken()

}