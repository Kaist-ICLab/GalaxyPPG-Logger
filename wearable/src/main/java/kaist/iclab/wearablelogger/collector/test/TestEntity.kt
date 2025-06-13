package kaist.iclab.wearablelogger.collector.test

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "testEvent"
)
data class TestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
)
