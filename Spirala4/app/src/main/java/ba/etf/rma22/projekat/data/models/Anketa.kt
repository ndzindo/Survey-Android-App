package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity
data class Anketa (
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    val naziv: String,
    val nazivIstrazivanja: String?,
    @ColumnInfo(name = "datumPocetak")
    val datumPocetak: Date,
    @ColumnInfo(name = "datumKraj")
    val datumKraj: Date?,
    @ColumnInfo(name = "datumRada")
    var datumRada: Date?,
    val trajanje: Int,
    @ColumnInfo(name = "nazivGrupe")
    val nazivGrupe: String? = "",
    @ColumnInfo(defaultValue = "false")
    var upisana: Boolean = false,
    var progres: Float?
) : Serializable {
    fun moguceRaditiAnketu(): Boolean =
        datumPocetak < Date() && datumKraj != null && datumKraj > Date() && datumRada == null
}