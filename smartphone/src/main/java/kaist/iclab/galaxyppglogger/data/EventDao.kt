package kaist.iclab.galaxyppglogger.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertEvent(eventEntity: EventEntity)

    @Query("SELECT * FROM event")
    fun getAllEvent(): Flow<List<EventEntity>>

    @Query("SELECT * FROM event")
    suspend fun getAll(): List<EventEntity>

    @Query("DELETE FROM event")
    suspend fun deleteAll()
}