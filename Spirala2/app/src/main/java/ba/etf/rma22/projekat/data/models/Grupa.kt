package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


data class Grupa(
    val naziv: String,
    val nazivIstrazivanja: String
) : Serializable