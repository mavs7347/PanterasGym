package com.proyectofinal.panterasgym

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DarkScreenActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark_screen2)

        // Finalizar esta actividad automáticamente después de un tiempo si es necesario
        window.setFlags(
            android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
}