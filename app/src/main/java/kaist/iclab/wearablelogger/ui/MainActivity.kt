package kaist.iclab.wearablelogger.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SendToMobile
import androidx.compose.material.icons.rounded.MonitorHeart
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.room.RoomDatabase
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.scrollAway
import com.google.android.gms.wearable.Wearable
import kaist.iclab.wearablelogger.ToggleStates
import kaist.iclab.wearablelogger.collector.CollectorRepository
import kotlinx.coroutines.delay
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.inject
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : ComponentActivity(){
    private val dataClient by lazy { Wearable.getDataClient(this) }
    private val db by lazy { get<RoomDatabase>()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val collectorRepository by inject<CollectorRepository>()
        val toggleStates: ToggleStates by inject()

        setContent {
            WearApp(
                collectorRepository,
//                onSendDataClick = null,
                toggleStates,
            )
        }
    }


//    private fun sendData(sensorStates: List<Boolean>) {
//        // TODO: Implement Another Sensor
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val ppgDataMapList = if (sensorStates[0]) loadAndFormatPpgData() else emptyList()
//                val accDataMapList = if (sensorStates[1]) loadAndFormatAccData() else emptyList()
//                val hrDataMapList = if (sensorStates[2]) loadAndFormatHrData() else emptyList()
//                val skinTempDataMapList = if (sensorStates[3]) loadAndFormatSkinTempData() else emptyList()
//                Log.d("joinedTest", (ppgDataMapList + accDataMapList + hrDataMapList + skinTempDataMapList).toString())
//                val joinedList = ArrayList(ppgDataMapList + accDataMapList + hrDataMapList + skinTempDataMapList)
//                if (joinedList.isNotEmpty()) {
//                    val request = PutDataMapRequest.create(DATA_PATH).apply {
//                        dataMap.putDataMapArrayList(DATA_KEY, joinedList)
//                    }
//                        .asPutDataRequest()
//                        .setUrgent()
//
//                    val result = dataClient.putDataItem(request).await()
//
//                    Log.d(TAG, "DataItem saved: $result")
//                }
//            } catch (cancellationException: CancellationException) {
//                throw cancellationException
//            } catch (exception: Exception) {
//                Log.d(TAG, "Saving DataItem failed: $exception")
//            }
//        }
//
//    }
//    private suspend fun loadAndFormatPpgData() : ArrayList<DataMap>{
//        val ppgDao = db.ppgDao()
//        val savedDataList: List<PpgEntity> = ppgDao.getAll()
//        Log.d(TAG, "savedPpgDataList: ${savedDataList.toString()}")
//        val alSavedDataList = (savedDataList.toTypedArray()).toCollection(ArrayList<PpgEntity>())
//        val dataMapList = ArrayList<DataMap>()
//        for (entity in alSavedDataList) {
//            val longArrEntity = longArrayOf(entity.timestamp, entity.ppgData.toLong())
//            val dataMap = DataMap().apply {
//                putLongArray(
//                    PPG_DATA_KEY,
//                    longArrEntity
//                )
//            }
//            Log.d("debuggingPpgDataType", "${longArrEntity.toList()}")
//            dataMapList.add(dataMap)
//        }
//        return dataMapList
//    }
//    private suspend fun loadAndFormatAccData() : ArrayList<DataMap>{
//        val accDao = db.accDao()
//        val savedDataList: List<AccEntity> = accDao.getAll()
//        Log.d(TAG, "savedAccDataList: ${savedDataList.toString()}")
//        val alSavedDataList = (savedDataList.toTypedArray()).toCollection(ArrayList<AccEntity>())
//        val dataMapList = ArrayList<DataMap>()
//        for (entity in alSavedDataList) {
//            val longArrEntity = longArrayOf(entity.timestamp, entity.accData.toLong())
//            val dataMap = DataMap().apply {
//                putLongArray(
//                    ACC_DATA_KEY,
//                    longArrEntity
//                )
//            }
//            Log.d("debuggingAccDataType", "${longArrEntity.toList()}")
//            dataMapList.add(dataMap)
//        }
//        return dataMapList
//    }
//    private suspend fun loadAndFormatHrData() : ArrayList<DataMap>{
//        val hrDao = db.hribiDao()
//        val savedDataList: List<HRIBIEntity> = hrDao.getAll()
//        Log.d(TAG, "savedHrDataList: ${savedDataList.toString()}")
//        val alSavedDataList = (savedDataList.toTypedArray()).toCollection(ArrayList<HRIBIEntity>())
//        val dataMapList = ArrayList<DataMap>()
//        for (entity in alSavedDataList) {
//            val longArrEntity = longArrayOf(entity.timestamp, entity.hribiData.toLong())
//            val dataMap = DataMap().apply {
//                putLongArray(
//                    HR_DATA_KEY,
//                    longArrEntity
//                )
//            }
//            Log.d("debuggingHrDataType", "${longArrEntity.toList()}")
//            dataMapList.add(dataMap)
//        }
//        return dataMapList
//    }
//    private suspend fun loadAndFormatSkinTempData() : ArrayList<DataMap>{
//        val skinTempDao = db.skintempDao()
//        val savedDataList: List<SkinTempEntity> = skinTempDao.getAll()
//        Log.d(TAG, "savedSkinTempDataList: ${savedDataList.toString()}")
//        val alSavedDataList = (savedDataList.toTypedArray()).toCollection(ArrayList<SkinTempEntity>())
//        val dataMapList = ArrayList<DataMap>()
//        for (entity in alSavedDataList) {
//            val longArrEntity = longArrayOf(entity.timestamp, entity.skinTempData.toLong())
//            val dataMap = DataMap().apply {
//                putLongArray(
//                    SKIN_TEMP_DATA_KEY,
//                    longArrEntity
//                )
//            }
//            Log.d("debuggingSkinTempDataType", "${longArrEntity.toList()}")
//            dataMapList.add(dataMap)
//        }
//        return dataMapList
//    }


    companion object {
        private const val TAG = "MainActivity"
        private const val DATA_PATH = "/data"
        private const val DATA_KEY = "data"
        private const val PPG_DATA_KEY = "ppg"
        private const val ACC_DATA_KEY = "acc"
        private const val HR_DATA_KEY = "hr"
        private const val SKIN_TEMP_DATA_KEY = "skintemp"

    }

}
@Composable
fun WearApp(
    collectorRepository: CollectorRepository,
//    onSendDataClick: (sensorStates: List<Boolean>) -> Unit?,
    toggleStates: ToggleStates
) {
//    Elapsed time related variables
    var isStartClicked by remember { mutableStateOf(false)}
    var startTime by remember { mutableStateOf(0L) }
    val timeFormat = remember { SimpleDateFormat("mm:ss", Locale.getDefault()) }
    var elapsedTime by remember { mutableStateOf(0L) }

    var buttonText by remember { mutableStateOf("Start")}
    var buttonColor = if (isStartClicked) MaterialTheme.colors.error else MaterialTheme.colors.primary
    val listState = rememberScalingLazyListState() // for scroll
//    var sensorStates = remember { mutableStateOf(List(4) {true}) }
    val sensorNames = listOf("PPG Green", "Accelerometer", "Heart Rate", "Skin Temperature")


    //Elapsed time
    LaunchedEffect(isStartClicked) {
        while (isStartClicked) {
            delay(1000)
            elapsedTime = System.currentTimeMillis() - startTime
        }
    }
    //fStart/Stop Button function
    fun toggleStartStopButton() {
        isStartClicked = !isStartClicked
        buttonText = if (isStartClicked) "Stop" else "Start"
        if (isStartClicked) {
            buttonText = "Stop"
            startTime = System.currentTimeMillis()
            elapsedTime = 0L
            collectorRepository.start()
        } else {
            buttonText = "Start"
            collectorRepository.stop()
        }
    }
    //UI
    Scaffold(
        timeText = {
                TimeText(modifier = Modifier.scrollAway(listState))
        },
        vignette = {
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        },
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = listState
            )
        }
    ){
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            autoCentering = AutoCenteringParams(itemIndex = 0),
            state = listState
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = buttonText,
                        color = Color.White,
                    )
                    if (isStartClicked) {
                        Text(
                            text = "Elapsed Time: ${timeFormat.format(elapsedTime)}",
                            color = Color.White,
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButtonWithIcon(
                        icon = Icons.Default.SendToMobile,
                        onClick = { /*TODO*/ },
//                        onClick = {onSendDataClick(toggleStates)},
                        contentDescription = "Sync icon",
                        backgroundColor = MaterialTheme.colors.secondary,
                        buttonSize = 32.dp,
                        iconSize = 20.dp
                    )
                    IconButtonWithIcon(
                        icon = if (isStartClicked) Icons.Rounded.Stop else Icons.Rounded.MonitorHeart,
                        onClick = {
                            toggleStartStopButton()
                        },
                        contentDescription = "toggles measuring action",
                        backgroundColor = buttonColor,
                        buttonSize = 48.dp,
                        iconSize = 36.dp,
                    )
                    IconButtonWithIcon(
                        icon = Icons.Default.Delete,
                        onClick = { collectorRepository.flush() },
                        contentDescription = "Reset icon",
                        backgroundColor = MaterialTheme.colors.secondary,
                        buttonSize = 32.dp,
                        iconSize = 20.dp
                    )
                }
            }
            sensorNames.forEach { sensorName ->
                item {
                    SensorToggleChip(sensorName = sensorName, toggleStates = toggleStates)
                }
            }
        }
    }
}