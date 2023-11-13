package ba.etf.rma22.projekat.view.pitanje

import android.graphics.Color
import android.util.Log
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

class OpcijeAdapter(private var opcije: List<String>, var odabranaOpcija: Int?, private val odabranaOpcijaItemClicked: OdabranaOpcijaItemClicked): RecyclerView.Adapter<OpcijeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_opcija, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val opcija = opcije[position]
        holder.bind(opcija)
    }

    override fun getItemCount(): Int {
        return opcije.size
    }

    interface OdabranaOpcijaItemClicked{
        fun onItemClickedListener(view: View, opcija: Int)
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        private val tvTekst: TextView = view.findViewById(R.id.tekst)

        fun bind(opcija: String) {

            tvTekst.text = opcija

            if (odabranaOpcija != null) {
                if (odabranaOpcija == adapterPosition) {
                    tvTekst.setTextColor(Color.parseColor("#0000FF"))
                } else {
                    tvTekst.setTextColor(Color.parseColor("#000000"))
                }
            }
            view.setOnClickListener {
                if (odabranaOpcija != adapterPosition) {

                    odabranaOpcijaItemClicked.onItemClickedListener(it, adapterPosition)
//                    notifyDataSetChanged()
                }

                Log.e("TAG", adapterPosition.toString() )
            }
        }

    }
}