package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje

@Dao
interface GrupaDao {

    @Query("SELECT * FROM grupa")
    suspend fun getAll(): List<Grupa>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg grupa: Grupa)

    @Query("DELETE FROM Grupa")
    suspend fun truncateTable(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEntities(body: List<Grupa>)

    @Query("SELECT * FROM Grupa WHERE upisana = :isUpisana")
    suspend fun getUpisane(isUpisana: Boolean = true): List<Grupa>

}