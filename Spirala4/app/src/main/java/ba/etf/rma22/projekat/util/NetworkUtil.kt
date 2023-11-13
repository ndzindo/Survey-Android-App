package ba.etf.rma22.projekat.util

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import ba.etf.rma22.projekat.data.AppDatabase

class NetworkUtil {

     companion object {
         var isDeviceConnected: Boolean = true
     }
}