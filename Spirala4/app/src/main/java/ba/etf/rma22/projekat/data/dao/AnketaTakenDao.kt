package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Odgovor

@Dao
interface AnketaTakenDao {

    @Query("SELECT * FROM AnketaTaken")
    suspend fun getAll(): List<AnketaTaken>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg anketaTaken: AnketaTaken)

    @Query("DELETE FROM AnketaTaken")
    suspend fun truncateTable(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEntities(poceteAnkete: List<AnketaTaken>)
}