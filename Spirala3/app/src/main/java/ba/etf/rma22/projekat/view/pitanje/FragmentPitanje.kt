package ba.etf.rma22.projekat.view.pitanje

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje

class FragmentPitanje : Fragment(), OpcijeAdapter.OdabranaOpcijaItemClicked {

    private lateinit var tvPitanje: TextView
    private lateinit var rvOdgovori: RecyclerView
    private lateinit var btnZaustavi: Button

    private lateinit var listOpcije: List<String>
    private lateinit var opcijeAdapter: OpcijeAdapter

    private lateinit var pitanje: Pitanje
    private lateinit var anketa: Anketa

    private val fragmentPitanjeViewModel = FramgentPitanjeViewModel()

    private var odabranaOpcija: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_pitanje, container, false)

        tvPitanje = view.findViewById(R.id.tekstPitanja)
        rvOdgovori = view.findViewById(R.id.odgovoriLista)
        btnZaustavi = view.findViewById(R.id.dugmeZaustavi)

        pitanje = arguments?.getSerializable("pitanje") as Pitanje
        anketa = arguments?.getSerializable("anketa") as Anketa

        tvPitanje.text = pitanje.tekstPitanja
        listOpcije = pitanje.opcije
        odabranaOpcija = fragmentPitanjeViewModel.getSacuvaniOdgovorZaPitanjeAnkete(pitanje.naziv, anketa.naziv, anketa.nazivIstrazivanja!!)

        opcijeAdapter = OpcijeAdapter(listOpcije, odabranaOpcija, this)
        rvOdgovori.layoutManager = LinearLayoutManager(requireContext())
        rvOdgovori.adapter = opcijeAdapter

        btnZaustavi.setOnClickListener {
            if (anketa.moguceRaditiAnketu()) {
                fragmentPitanjeViewModel.updateProgresForAnketa(anketa)
            }
            (activity as MainActivity).zaustaviAnketu()
        }
        return view
    }

    companion object {
        fun newInstance(pitanje: Pitanje, anketa: Anketa) : FragmentPitanje {
            val fp = FragmentPitanje()
            val bundle = Bundle()
            bundle.putSerializable("pitanje", pitanje)
            bundle.putSerializable("anketa", anketa)
            fp.arguments = bundle
            return fp
        }
    }

    override fun onItemClickedListener(view: View, opcija: Int) {
        if (anketa.moguceRaditiAnketu()) {
            val tvTekst: TextView = view.findViewById(R.id.tekst)
            tvTekst.setTextColor(Color.parseColor("#0000FF"))
            opcijeAdapter.odabranaOpcija = opcija
            odabranaOpcija = opcija
            sacuvajOdgovor(opcija)
            opcijeAdapter.notifyDataSetChanged()
        }
    }

    private fun sacuvajOdgovor(opcija: Int) {
        fragmentPitanjeViewModel.updateSacuvaniOdgovorZaPitanjeAnkete(pitanje.naziv, anketa.naziv, anketa.nazivIstrazivanja!!, opcija)
    }
}