package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Odgovor

@Dao
interface OdgovorDao {

    @Query("SELECT * FROM odgovor")
    suspend fun getAll(): List<Odgovor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg odgovor: Odgovor)

    @Query("DELETE FROM Odgovor")
    suspend fun truncateTable(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEntities(odgovori: List<Odgovor>)

    @Query("SELECT * FROM Odgovor WHERE anketaId = :idAnkete")
    suspend fun getByAnketaId(idAnkete: Int): List<Odgovor>
}