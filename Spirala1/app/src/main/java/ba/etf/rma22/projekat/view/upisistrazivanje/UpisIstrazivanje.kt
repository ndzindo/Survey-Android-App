package ba.etf.rma22.projekat.view.upisistrazivanje

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.util.CustomUtils

class UpisIstrazivanje : AppCompatActivity() {

    private lateinit var sGodine: Spinner
    private lateinit var sIstrazivanja: Spinner
    private lateinit var sGrupe: Spinner
    private lateinit var btnUpisiMe: Button

    private val upisViewModel = UpisViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upis_istrazivanje)

        initViews()
        populateGodinaSpinner()

        btnUpisiMe.setOnClickListener {
            upisiKorisnikaUIstrazivanjeIGrupu()
        }

        sGodine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                prikaziNeupisanaIstrazivanja((sGodine.getItemAtPosition(p2) as String).toInt())
                checkAllSpinnerStatus()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        sIstrazivanja.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                prikaziNeupisaneGrupe(sIstrazivanja.getItemAtPosition(p2) as String)
                checkAllSpinnerStatus()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun checkAllSpinnerStatus() {
        btnUpisiMe.isEnabled =
            !(sGodine.selectedItem == null || sIstrazivanja.selectedItem == null || sGrupe.selectedItem == null)
    }

    private fun prikaziNeupisaneGrupe(istrazivanje: String) {
        val neupisaneGrupe = upisViewModel.getNeupisaneGrupeByIstrazivanje(istrazivanje)
        val neupisneGrupeNazivi =
            neupisaneGrupe.map { grupa -> grupa.naziv }

        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, neupisneGrupeNazivi)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sGrupe.adapter = arrayAdapter
    }

    private fun prikaziNeupisanaIstrazivanja(godina: Int) {
        val neupisanaIstrazivanja = upisViewModel.getNeupisanaIstrazivanjaByGodina(godina)
        val neupisnaIstrazivanjaNazivi =
            neupisanaIstrazivanja.map { istrazivanje -> istrazivanje.naziv }

        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, neupisnaIstrazivanjaNazivi)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sIstrazivanja.adapter = arrayAdapter
    }

    private fun upisiKorisnikaUIstrazivanjeIGrupu() {
        Korisnik.indexPrethodnoOdabraneGodine = sGodine.selectedItemPosition
        Korisnik.upisanaIstrazivanja.add(sIstrazivanja.selectedItem as String)
        Korisnik.upisaneGrupe.add(sGrupe.selectedItem as String)
        finish()
    }

    private fun populateGodinaSpinner() {
        val godineList = CustomUtils.godineList
        val indexPrethodnoOdabraneGodine: Int

        indexPrethodnoOdabraneGodine = if (Korisnik.indexPrethodnoOdabraneGodine == -1) {
            0
        } else {
            Korisnik.indexPrethodnoOdabraneGodine
        }

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, godineList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sGodine.adapter = arrayAdapter

        sGodine.setSelection(indexPrethodnoOdabraneGodine)
    }

    private fun initViews() {
        sGodine = findViewById(R.id.odabirGodina)
        sIstrazivanja = findViewById(R.id.odabirIstrazivanja)
        sGrupe = findViewById(R.id.odabirGrupa)
        btnUpisiMe = findViewById(R.id.dodajIstrazivanjeDugme)
    }
}