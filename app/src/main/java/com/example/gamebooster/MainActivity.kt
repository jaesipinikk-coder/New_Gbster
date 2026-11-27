package com.example.gamebooster

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 48, 48, 48)
        }

        val title = TextView(this).apply {
            text = "Game Booster"
            textSize = 24f
        }

        val startButton = Button(this).apply {
            text = "Mulakan Boost"
            setOnClickListener {
                text = "Mod permainan aktif"
            }
        }

        layout.addView(title)
        layout.addView(startButton)
        setContentView(layout)
    }
}￼Enter
