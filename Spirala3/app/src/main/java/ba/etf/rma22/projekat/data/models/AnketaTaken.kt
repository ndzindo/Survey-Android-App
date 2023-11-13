package ba.etf.rma22.projekat.data.models

import java.io.Serializable
import java.util.*

data class AnketaTaken (
    val id: Int,
    var progres: Int,
    val student: String,
    val datumRada: Date,
    val AnketumId: Int
    ) : Serializable