package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Pitanje(
    @PrimaryKey
    val id: Int,
    val naziv: String,
    @ColumnInfo(name = "tekstPitanja")
    val tekstPitanja: String,
    val opcije: List<String>,
    @ColumnInfo(name = "anketaId", defaultValue = "-1")
    var anketaId: Int = -1
) : Serializable
