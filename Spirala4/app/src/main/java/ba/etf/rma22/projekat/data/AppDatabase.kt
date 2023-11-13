package ba.etf.rma22.projekat.data

import android.content.Context
import androidx.room.*
import ba.etf.rma22.projekat.data.dao.*
import ba.etf.rma22.projekat.data.models.*

@Database(
    entities = [Account::class, Anketa::class, AnketaTaken::class, Grupa::class, Istrazivanje::class, Odgovor::class, Pitanje::class],
    version = 2
)
@TypeConverters(DateConverter::class, ListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun anketaDao(): AnketaDao
    abstract fun anketaTakenDao(): AnketaTakenDao
    abstract fun grupaDao(): GrupaDao
    abstract fun istrazivanjeDao(): IstrazivanjeDao
    abstract fun odgovorDao(): OdgovorDao
    abstract fun pitanjeDao(): PitanjeDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun createInstance(context: Context) {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
        }

        fun getInstance(): AppDatabase {
            return INSTANCE!!
        }

        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "RMA22DB"
            ).build()
    }
}