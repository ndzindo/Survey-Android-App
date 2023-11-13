package ba.etf.rma22.projekat.data.models

import java.io.Serializable

data class Pitanje(val naziv: String, val tekst: String, val opcije: List<String>) : Serializable
