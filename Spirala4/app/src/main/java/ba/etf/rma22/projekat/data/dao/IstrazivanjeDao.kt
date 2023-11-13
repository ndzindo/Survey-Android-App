package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Istrazivanje

@Dao
interface IstrazivanjeDao {

    @Query("SELECT * FROM istrazivanje")
    suspend fun getAll(): List<Istrazivanje>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg istrazivanje: Istrazivanje)

    @Query("DELETE FROM Istrazivanje")
    suspend fun truncateTable(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEntities(body: List<Istrazivanje>)

    @Query("SELECT * FROM Istrazivanje LIMIT 5 OFFSET :offset")
    suspend fun getNthFive(offset: Int): List<Istrazivanje>
}