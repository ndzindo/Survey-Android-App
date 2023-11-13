package ba.etf.rma22.projekat.view.anketa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class FragmentAnkete : Fragment() {

    private lateinit var rvAnketa: RecyclerView
    private lateinit var sFilter: Spinner
    private lateinit var btnUpis: FloatingActionButton

    private lateinit var listAnketa: List<Anketa>
    private lateinit var anketaAdapter: AnketaAdapter

    private val anketaListViewModel = AnketaListViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_ankete, container, false)
        initViews(view)
        populateSpinner()

        listAnketa = anketaListViewModel.getMyAnkete()
        listAnketa = listAnketa.sortedBy { anketa -> anketa.datumPocetak }

        anketaAdapter = AnketaAdapter(listAnketa)
        rvAnketa.layoutManager = GridLayoutManager(requireContext(), 2)
        rvAnketa.adapter = anketaAdapter
        anketaAdapter.onItemClick = { anketa ->
            (activity as MainActivity).prikaziPitanjaZaAnketu(anketa)
        }

        sFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val filterType = parent?.getItemAtPosition(position).toString()

                updateAnketaList(filterType)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        return view
    }

    private fun updateAnketaList(filterType: String) {
        when (filterType) {
            "Sve moje ankete" -> {
                val newList = anketaListViewModel.getMyAnkete()
                anketaAdapter.setAnketaList(newList)
            }
            "Sve ankete" -> {
                val newList = anketaListViewModel.getAll(::onGetAnketeSuccess, ::onGetAnketeError)
            }
            "Urađene ankete" -> {
                val newList = anketaListViewModel.getDoneAnkete()
                anketaAdapter.setAnketaList(newList)
            }
            "Buduće ankete" -> {
                val currentDate = Date()
//                val newList = anketaListViewModel.getMyAnkete().filter { anketa -> anketa.datumPocetak > currentDate || (anketa.datumPocetak < currentDate && anketa.datumKraj > currentDate && anketa.datumRada == null) }
                val newList = anketaListViewModel.getFutureAnkete()
                anketaAdapter.setAnketaList(newList)
            }
            "Prošle ankete" -> {
                val currentDate = Date()
//                val newList = anketaListViewModel.getMyAnkete().filter { anketa -> anketa.datumKraj < currentDate && anketa.datumRada == null }
                val newList = anketaListViewModel.getNotTakenAnkete()
                anketaAdapter.setAnketaList(newList)
            }
        }
    }

    private fun populateSpinner() {
        val arrayList: List<String> = listOf(
            "Sve moje ankete",
            "Sve ankete",
            "Urađene ankete",
            "Buduće ankete",
            "Prošle ankete"
        )

        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sFilter.adapter = arrayAdapter
    }

    private fun initViews(view: View) {
        rvAnketa = view.findViewById(R.id.listaAnketa)
        sFilter = view.findViewById(R.id.filterAnketa)
    }

    private fun onGetAnketeSuccess(newList: List<Anketa>){
        anketaAdapter.setAnketaList(newList)
    }

    private fun onGetAnketeError(message: String){
        Toast.makeText(requireContext(), "Error: ${message}", Toast.LENGTH_SHORT).show()
    }
}