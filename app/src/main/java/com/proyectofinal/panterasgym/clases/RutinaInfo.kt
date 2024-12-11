package com.proyectofinal.panterasgym.clases

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.proyectofinal.panterasgym.R

class RutinaInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rutina_info)

        // Contenedor dinámico para los ejercicios
        val contenedorEjercicios: LinearLayout = findViewById(R.id.contenedorEjercicios)

        // Obtener datos del Intent
        val ejercicios = intent.getSerializableExtra("ejercicios") as? List<Ejercicio>

        if (ejercicios != null) {
            ejercicios.forEach { ejercicio ->
                agregarVistaEjercicio(contenedorEjercicios, ejercicio)
            }
        } else {
            Toast.makeText(this, "No hay ejercicios disponibles.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun agregarVistaEjercicio(contenedor: LinearLayout, ejercicio: Ejercicio) {
        // Crear un contenedor para cada ejercicio
        val ejercicioContainer = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(16, 16, 16, 16) // Margen alrededor del contenedor
            }
            orientation = LinearLayout.VERTICAL
        }

        // Crear TextView para el nombre
        val textViewNombre = TextView(this).apply {
            text = "Nombre: ${ejercicio.eNombre}"
            textSize = 18f
            setPadding(8, 8, 8, 8)
        }
        ejercicioContainer.addView(textViewNombre)

        // Crear VideoView con dimensiones ajustadas
        val videoView = VideoView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 600
            ).apply {
                setMargins(0, 8, 0, 8)
            }
        }
        ejercicioContainer.addView(videoView)
        configurarVideo(videoView, ejercicio.eNombre)

        // Crear TextView para la descripción
        val textViewDescripcion = TextView(this).apply {
            text = "Descripción: ${ejercicio.eDescripcion}"
            setPadding(8, 8, 8, 8)
        }
        ejercicioContainer.addView(textViewDescripcion)

        // Añadir el contenedor completo al layout principal
        contenedor.addView(ejercicioContainer)
    }


    private fun configurarVideo(videoView: VideoView, nombreVideo: String?) {
        val resourceId = getRawResourceId(nombreVideo)
        if (resourceId != null) {
            val uri = Uri.parse("android.resource://${packageName}/$resourceId")
            val mediaController = MediaController(this)
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)
            videoView.setVideoURI(uri)
            videoView.requestFocus()
            videoView.setOnPreparedListener {
                Toast.makeText(this, "Video listo para reproducir", Toast.LENGTH_SHORT).show()
            }
            videoView.setOnErrorListener { _, _, _ ->
                Toast.makeText(this, "Error al reproducir el video.", Toast.LENGTH_SHORT).show()
                true
            }
        } else {
            Toast.makeText(this, "Video no disponible.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getRawResourceId(fileName: String?): Int? {
        return try {
            fileName?.let {
                val resourceId = resources.getIdentifier(it, "raw", packageName)
                if (resourceId != 0) {
                    resourceId
                } else {
                    Log.e("RutinaInfo", "Recurso no encontrado: $fileName")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("RutinaInfo", "Error al obtener recurso: ${e.message}")
            null
        }
    }
}
