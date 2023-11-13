package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Odgovor(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    val odgovoreno: Int,
    @ColumnInfo(name = "anketaId", defaultValue = "-1")
    var anketaId: Int = -1
)
