package ba.etf.rma22.projekat.data.repositories

import android.app.Application
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Account
import ba.etf.rma22.projekat.data.models.Grupa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountRepository {
    var acHash: String = "7835fd06-d070-4bc8-aeca-1812ca8e279e"

    suspend fun postaviHash(payload: String): Boolean {

        // TODO upisati u bazu
        val hash = getHash()

        if (hash != null && payload == hash) {
            // isti hash nista ne radi
            return false
        } else {
            withContext(Dispatchers.IO) {
                val appDatabaseInstance = AppDatabase.getInstance()

                //novi hash, obrisi podatke
                appDatabaseInstance.accountDao().truncateTable()
                appDatabaseInstance.anketaDao().truncateTable()
                appDatabaseInstance.anketaTakenDao().truncateTable()
                appDatabaseInstance.grupaDao().truncateTable()
                appDatabaseInstance.istrazivanjeDao().truncateTable()
                appDatabaseInstance.odgovorDao().truncateTable()
                appDatabaseInstance.pitanjeDao().truncateTable()

                //upisi novog korisnika u bazu
                appDatabaseInstance.accountDao().insertAccount(Account(payload))
            }
        }
        return true
    }

    suspend fun getHash(): String? {
        return withContext(Dispatchers.IO) {
            AppDatabase.getInstance().accountDao().getAccount()?.acHash
        }
    }
}