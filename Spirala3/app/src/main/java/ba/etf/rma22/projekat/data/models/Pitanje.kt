package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pitanje(val id: Int,
                   val naziv: String,
                   val tekstPitanja: String,
                   val opcije: List<String>
                   ) : Serializable
