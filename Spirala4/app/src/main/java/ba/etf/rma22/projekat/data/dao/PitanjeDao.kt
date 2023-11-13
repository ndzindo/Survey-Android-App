package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.Pitanje

@Dao
interface PitanjeDao {

    @Query("SELECT * FROM pitanje")
    suspend fun getAll(): List<Pitanje>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg pitanje: Pitanje)

    @Query("DELETE FROM Pitanje")
    suspend fun truncateTable(): Int

    @Query("SELECT * FROM Pitanje WHERE anketaId = :idAnkete")
    suspend fun getByAnketumId(idAnkete: Int): List<Pitanje>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEntities(pitanja: List<Pitanje>)
}