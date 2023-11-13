package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Grupa(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    val naziv: String,
    val nazivIstrazivanja: String? = "",
    @ColumnInfo(name = "upisana", defaultValue = "false")
    var upisana: Boolean = false,
    val istrazivanjeId: Int? = 0
) : Serializable
