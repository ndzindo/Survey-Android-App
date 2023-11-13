package ba.etf.rma22.projekat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Istrazivanje(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    val naziv: String,
    val godina: Int
)

//Godina 1-5 limit