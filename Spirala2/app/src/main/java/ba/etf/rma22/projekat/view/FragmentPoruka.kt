package ba.etf.rma22.projekat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa

class FragmentPoruka() : Fragment() {

    private lateinit var tvPoruka: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_poruka, container, false)

        tvPoruka = view.findViewById(R.id.tvPoruka)

        val tip = arguments?.getString("tip")

        if (tip!! == "grupa") {
            val grupa = arguments?.getSerializable("grupa") as Grupa

            tvPoruka.text = "Uspješno ste upisani u\n" +
                    "grupu ${grupa.naziv} istraživanja ${grupa.nazivIstrazivanja}!"
        } else {
            val anketa = arguments?.getSerializable("anketa") as Anketa
            tvPoruka.text = "Završili ste anketu ${anketa.naziv} u okviru istraživanja ${anketa.nazivIstrazivanja}"
        }

        return view
    }

    companion object {
        fun newInstance(grupa: Grupa): FragmentPoruka {
            val fp = FragmentPoruka();
            val bundle = Bundle()
            bundle.putString("tip", "grupa")
            bundle.putSerializable("grupa", grupa)
            fp.arguments = bundle
            return fp
        }

        fun newInstance(anketa: Anketa): FragmentPoruka {
            val fp = FragmentPoruka();
            val bundle = Bundle()
            bundle.putString("tip", "anketa")
            bundle.putSerializable("anketa", anketa)
            fp.arguments = bundle
            return fp
        }
    }


}