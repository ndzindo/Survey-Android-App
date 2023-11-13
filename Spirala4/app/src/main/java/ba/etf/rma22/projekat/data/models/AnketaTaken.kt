package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity
data class AnketaTaken (
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    var progres: Int,
    val student: String,
    @ColumnInfo(name = "datumRada")
    val datumRada: Date,
    @ColumnInfo(name = "AnketumId")
    val AnketumId: Int = -1
    ) : Serializable