package ba.etf.rma22.projekat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.data.models.Anketa
import java.util.*
import android.widget.ArrayAdapter
import ba.etf.rma22.projekat.view.main.AnketaAdapter
import ba.etf.rma22.projekat.view.main.AnketaListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ba.etf.rma22.projekat.view.upisistrazivanje.UpisIstrazivanje


class MainActivity : AppCompatActivity() {

    private lateinit var rvAnketa: RecyclerView
    private lateinit var sFilter: Spinner
    private lateinit var btnUpis: FloatingActionButton

    private lateinit var listAnketa: List<Anketa>
    private lateinit var anketaAdapter: AnketaAdapter

    private val anketaListViewModel = AnketaListViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        populateSpinner()

        listAnketa = anketaListViewModel.getMyAnkete()
        listAnketa = listAnketa.sortedBy { anketa -> anketa.datumPocetak  }

        anketaAdapter = AnketaAdapter(listAnketa)
        rvAnketa.layoutManager = GridLayoutManager(this, 2)
        rvAnketa.adapter = anketaAdapter

        sFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val filterType = parent?.getItemAtPosition(position).toString()
                
                updateAnketaList(filterType)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        btnUpis.setOnClickListener {
            val intent = Intent(this, UpisIstrazivanje::class.java)
            startActivity(intent)
        }
    }

    private fun updateAnketaList(filterType: String) {
        when(filterType) {
            "Sve moje ankete" -> {
                val newList = anketaListViewModel.getMyAnkete()
                anketaAdapter.setAnketaList(newList)
            }
            "Sve ankete" -> {
                val newList = anketaListViewModel.getAll()
                anketaAdapter.setAnketaList(newList)
            }
            "Urađene ankete" -> {
//                val newList = anketaListViewModel.getMyAnkete().filter { anketa -> anketa.datumRada != null }
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

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sFilter.adapter = arrayAdapter
    }

    private fun initViews() {
        rvAnketa = findViewById(R.id.listaAnketa)
        sFilter = findViewById(R.id.filterAnketa)
        btnUpis = findViewById(R.id.upisDugme)
    }
}