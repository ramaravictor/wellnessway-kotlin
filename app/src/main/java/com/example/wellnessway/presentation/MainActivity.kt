package com.example.wellnessway.presentation


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.wellnessway.WellnessWayApp
import com.example.wellnessway.presentation.theme.WellnessWayTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WellnessWayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WellnessWayApp()
                }
            }
        }
    }
}

//class MainActivity : AppCompatActivity(){
//    private var sensorManager: SensorManager? = null
//
//    private var running = false
//    private var totalStep = 0f
//    private var previousTotalStep = 0f
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//    }
//
//    override fun onResume() {
//        super.onResume()
//        running = true
//        val stepSensor : Sensor? = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
//
//        if (stepSensor == null) {
//            Toast.makeText(
//                this,
//                "Step counter sensor is not present on this device",
//                Toast.LENGTH_SHORT
//            ).show()
//        }else{
//            sensorManager?.registerListener(this, stepSensor,SensorManager.SENSOR_DELAY_UI)
//        }
//    }
//
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy:Int){
//
//    }
//
//    override fun onSensorChanged(event: SensorEvent?) {
//        if (running){
//            totalStep = event!!.values[0]
//            val currentStep = totalStep.toInt() - previousTotalStep.toInt()
//            tv_stepsTaken.text = ("$currentStep")
//
//            progress_circular.apply{
//                setProgressWithAnimation(currentStep.toFloat())
//            }
//        }
//    }
//
//    private fun resetSteps(){
//        tv_stepsTaken.setOnClickListener{
//            Toast.makeText(this, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
//        }
//
//        tv_stepsTaken.setOnLongClickListener{
//            previousTotalStep = totalStep
//            tv_stepsTaken.Text = 0.toString()
//            saveData()
//
//            true ^setOnLongClickListener
//        }
//    }
//
//    private fun saveData(){
//        val sharedPreferences = getSharedPreferences('myPrefs', Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putFloat()
//    }
//}