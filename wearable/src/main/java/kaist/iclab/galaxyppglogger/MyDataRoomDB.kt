package kaist.iclab.galaxyppglogger

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import kaist.iclab.galaxyppglogger.collector.ACC.AccDao
import kaist.iclab.galaxyppglogger.collector.ACC.AccEntity
import kaist.iclab.galaxyppglogger.collector.HR.HRDao
import kaist.iclab.galaxyppglogger.collector.HR.HREntity
import kaist.iclab.galaxyppglogger.collector.PPGGreen.PpgDao
import kaist.iclab.galaxyppglogger.collector.PPGGreen.PpgEntity
import kaist.iclab.galaxyppglogger.collector.SkinTemp.SkinTempDao
import kaist.iclab.galaxyppglogger.collector.SkinTemp.SkinTempEntity
import kaist.iclab.galaxyppglogger.collector.Test.TestDao
import kaist.iclab.galaxyppglogger.collector.Test.TestEntity

@Database(
    version = 15,
    entities = [
        TestEntity::class,
        PpgEntity::class,
        AccEntity::class,
        HREntity::class,
        SkinTempEntity::class,
    ],
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class MyDataRoomDB:RoomDatabase() {
    abstract fun testDao(): TestDao
    abstract fun ppgDao(): PpgDao
    abstract fun accDao(): AccDao
    abstract fun hrDao(): HRDao
    abstract fun skinTempDao(): SkinTempDao
}

class Converters {
    @TypeConverter
    fun listToJson(value: List<Int>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value,Array<Int>::class.java).toList()
}