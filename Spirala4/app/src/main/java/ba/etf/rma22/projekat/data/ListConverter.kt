package ba.etf.rma22.projekat.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class ListConverter {
    @TypeConverter
    fun fromStringList(countryLang: List<String>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toStringList(countryLangString: String?): List<String>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String>?>() {}.type
        return gson.fromJson<List<String>>(countryLangString, type)
    }
}