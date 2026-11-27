package com.example.gamebooster

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvStats: TextView
    private lateinit var btnBoost: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStats = findViewById(R.id.tvStats)
        btnBoost = findViewById(R.id.btnBoost)

        kemaskiniStatusSistem()

        btnBoost.setOnClickListener {
            bersihkanRam()
            kemaskiniStatusSistem()
            Toast.makeText(this, "Sistem Dioptimumkan! Sedia untuk bermain.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun kemaskiniStatusSistem() {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        val ramTersediaMB = memoryInfo.availMem / (1024 * 1024)
        val ramJumlahMB = memoryInfo.totalMem / (1024 * 1024)
        val suhuBateri = dapatkanSuhuBateri()

        tvStats.text = "RAM Tersedia: $ramTersediaMB MB / $ramJumlahMB MB\nSuhu Bateri: $suhuBateri °C"
    }

    private fun bersihkanRam() {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val pakejTerpasang = packageManager.getInstalledApplications(0)
        for (app in pakejTerpasang) {
            if (app.packageName != packageName) {
                activityManager.killBackgroundProcesses(app.packageName)
            }
        }
    }

    private fun dapatkanSuhuBateri(): Float {
        val intent = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val suhu = intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) ?: 0
        return suhu / 10.0f
    }

    private fun lancarkanGame(packageName: String) {
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            bersihkanRam()
            startActivity(launchIntent)
        } else {
            Toast.makeText(this, "Game tidak ditemui pada peranti ini.", Toast.LENGTH_SHORT).show()
        }
    }
}
