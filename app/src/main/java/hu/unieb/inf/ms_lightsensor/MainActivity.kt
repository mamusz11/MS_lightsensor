package hu.unieb.inf.ms_lightsensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var fenyero: Sensor? = null
    private lateinit var text: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById(R.id.luxvalue)

        setUpSensor()
    }
    private fun setUpSensor() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        fenyero = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0?.sensor?.type == Sensor.TYPE_LIGHT) {
            val light1 = p0.values[0]

            text.text = "Sensor: $light1\n${brightness(light1)}"

        }
    }

    private fun brightness(brightness: Float): String {

        return when (brightness.toInt()) {
            0 -> "Vak sötét"
            in 1..20 -> "Sötér"
            in 21..100 -> "Szürkület"
            in 101..5000 -> "Világos"
            in 5001..25000 -> "Nagyon világos"
            else -> "Nagon nagyon nagyon vilagos"
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, fenyero, SensorManager.SENSOR_DELAY_NORMAL)
    }


    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}