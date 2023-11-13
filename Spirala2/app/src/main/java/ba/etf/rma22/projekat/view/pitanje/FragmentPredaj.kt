package ba.etf.rma22.projekat.view.pitanje

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje
import java.util.*

class FragmentPredaj : Fragment() {

    private lateinit var tvProgresText: TextView
    private lateinit var btnPredaj: Button

    private lateinit var anketa: Anketa
    private val framgentPredajViewModel = FramgentPredajViewModel()

    private var progress: Float = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_predaj, container, false)

        tvProgresText = view.findViewById(R.id.progresTekst)
        btnPredaj = view.findViewById(R.id.dugmePredaj)

        anketa = arguments?.getSerializable("anketa") as Anketa

        btnPredaj.setOnClickListener {
                framgentPredajViewModel.predajAnketu(anketa, progress)
                (activity as MainActivity).predajAnketu(anketa)

        }

        return view
    }

    override fun onResume() {
        super.onResume()
        progress = framgentPredajViewModel.getProgresForAnketa(anketa)
        tvProgresText.text = getProgressRounded(progress).toString() + "%"

        if (!anketa.moguceRaditiAnketu()) {
            btnPredaj.isEnabled = false
        }
    }

    private fun getProgressRounded(progress: Float): Int {
        return when (progress) {
            in 0.000000001..0.19999999999999 -> 20
            in 0.2..0.39999999999999 -> 40
            in 0.4..0.59999999999999 -> 60
            in 0.6..0.79999999999999 -> 80
            in 0.8..1.0 -> 100
            else -> {
                0
            }
        }
    }

    companion object {
        fun newInstance(anketa: Anketa): FragmentPredaj {
            val fp = FragmentPredaj()
            val bundle = Bundle()
            bundle.putSerializable("anketa", anketa)
            fp.arguments = bundle
            return fp
        }
    }

}