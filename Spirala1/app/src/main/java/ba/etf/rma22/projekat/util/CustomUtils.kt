package ba.etf.rma22.projekat.util

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

object CustomUtils {

    val godineList: List<String> = listOf("1", "2", "3", "4", "5")

    fun formatDate(date: Date) = SimpleDateFormat("dd.MM.yyyy").format(date)
}