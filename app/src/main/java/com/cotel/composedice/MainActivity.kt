package com.cotel.composedice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.cotel.composedice.diceroller.DiceRollerScreen
import com.cotel.composedice.ui.ComposeDiceTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDiceTheme {
                DiceRollerScreen()
            }
        }
    }
}