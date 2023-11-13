package ba.etf.rma22.projekat.data.models

import java.io.Serializable
import java.util.*

data class Anketa (
    val id: Int,
    val naziv: String,
    val nazivIstrazivanja: String?,
    val datumPocetak: Date,
    val datumKraj: Date?,
    var datumRada: Date?,
    val trajanje: Int,
    val nazivGrupe: String?,
    var progres: Float?
) : Serializable {
    fun moguceRaditiAnketu(): Boolean =
        datumPocetak < Date() && datumKraj != null && datumKraj > Date() && datumRada == null
}