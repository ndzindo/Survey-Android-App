package ba.etf.rma22.projekat.view.anketa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.util.CustomUtils
import java.util.*

class AnketaAdapter(private var anketaList: List<Anketa>): RecyclerView.Adapter<AnketaAdapter.ViewHolder>() {

    var onItemClick: ((Anketa) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anketa, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val anketa = anketaList[position]
        holder.bind(anketa)
    }

    override fun getItemCount(): Int {
        return anketaList.size
    }

    fun setAnketaList(newAnketaList: List<Anketa>) {
        anketaList = newAnketaList.sortedBy { anketa -> anketa.datumPocetak  }

        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        private val tvAnketaNaziv: TextView = view.findViewById(R.id.tv_naziv)
        private val tvAnketaIstrazivanje: TextView = view.findViewById(R.id.tv_istrazivanje_broj)
        private val pbProgres: ProgressBar = view.findViewById(R.id.progresZavrsetka)
        private val ivStatus: ImageView = view.findViewById(R.id.iv_status)
        private val tvPoruka: TextView = view.findViewById(R.id.tv_poruka)
        private val tvDatum: TextView = view.findViewById(R.id.tv_datum)

        fun bind(anketa: Anketa) {
            tvAnketaNaziv.text = anketa.naziv
            tvAnketaIstrazivanje.text = anketa.nazivIstrazivanja
            ivStatus.setImageResource(getImageResourceForStatus(anketa))
            pbProgres.progress = getProgressRounded(anketa.progres)
            setDateMessage(anketa)
            view.setOnClickListener{
                onItemClick?.invoke(anketa)
            }
        }

        private fun getImageResourceForStatus(anketa: Anketa): Int {
            return when {
                anketa.datumRada != null -> R.drawable.plava
                anketa.datumPocetak < Date() && anketa.datumKraj > Date() -> R.drawable.zelena
                anketa.datumPocetak > Date() -> R.drawable.zuta
                anketa.datumKraj < Date() && anketa.datumRada == null -> R.drawable.crvena
                else -> {
                    R.drawable.crvena
                }
            }
        }

        private fun getProgressRounded(progress: Float): Int {
            return when(progress) {
                in 0.000000001 .. 0.19999999999999 -> 20
                in 0.2 .. 0.39999999999999 -> 40
                in 0.4 .. 0.59999999999999 -> 60
                in 0.6 .. 0.79999999999999 -> 80
                in 0.8 .. 1.0 -> 100
                else -> {0}
            }
        }

        private fun setDateMessage(anketa: Anketa) {
            val currentDate = Date()
            when {
                anketa.datumPocetak > currentDate -> {
                    tvPoruka.text = "Vrijeme aktiviranja: "
                    tvDatum.text = CustomUtils.formatDate(anketa.datumPocetak)
                    return
                }
                anketa.datumRada != null -> {
                    tvPoruka.text = "Anketa urađena: "
                    tvDatum.text = CustomUtils.formatDate(anketa.datumRada!!)
                    return
                }
                anketa.datumPocetak < currentDate && anketa.datumKraj > currentDate -> {
                    tvPoruka.text = "Vrijeme zatvaranja: "
                    tvDatum.text = CustomUtils.formatDate(anketa.datumKraj)
                    return
                }
                anketa.datumKraj < currentDate -> {
                    tvPoruka.text = "Anketa zatvorena: "
                    tvDatum.text = CustomUtils.formatDate(anketa.datumKraj)
                }
            }
        }
    }
}