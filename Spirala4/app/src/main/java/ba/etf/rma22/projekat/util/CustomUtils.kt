package ba.etf.rma22.projekat.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

object CustomUtils {

    val godineList: List<String> = listOf("1", "2", "3", "4", "5")

    fun formatDate(date: Date) = SimpleDateFormat("dd.MM.yyyy").format(date)
}

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)