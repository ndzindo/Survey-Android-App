package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma22.projekat.data.models.Anketa

@Dao
interface AnketaDao {

    @Query("SELECT * FROM anketa")
    suspend fun getAll(): List<Anketa>

    @Query("SELECT * FROM Anketa LIMIT 5 OFFSET :offset")
    suspend fun getNthFive(offset: Int): List<Anketa>

    @Query("SELECT * FROM Anketa WHERE id = :id")
    suspend fun getById(id: Int): Anketa?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg anketas: Anketa)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEntities(objects: List<Anketa>)

    @Query("DELETE FROM Anketa")
    suspend fun truncateTable(): Int

    @Query("SELECT * FROM Anketa WHERE upisana = :isUpisana")
    suspend fun getUpisane(isUpisana: Boolean = true): List<Anketa>
}