package com.proyectofinal.panterasgym.acceso

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.proyectofinal.panterasgym.R
import com.proyectofinal.panterasgym.clases.Cliente

class RegistroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var nombre: EditText
    private lateinit var correo: EditText
    private lateinit var contra: EditText
    private lateinit var edad: EditText
    private lateinit var peso: EditText
    private lateinit var altura: EditText

    private lateinit var registrar: Button
    private lateinit var regresar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            val appPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
            setThemeFromPreferences(appPreferences, this)

            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_registro)

            auth = FirebaseAuth.getInstance()
            db = FirebaseFirestore.getInstance()

            nombre = findViewById(R.id.edtNombre)
            correo = findViewById(R.id.edtCorreo)
            contra = findViewById(R.id.edtContrasena)
            edad = findViewById(R.id.edtEdad)
            peso = findViewById(R.id.edtPeso)
            altura = findViewById(R.id.edtAltura)

            registrar = findViewById(R.id.btnRegistrar)
            regresar = findViewById(R.id.lblRegresar)

            registrar.setOnClickListener { registrarUsuario() }
            regresar.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        } catch (e: Exception) {
            Log.e("RegistroActivity", "Error durante onCreate: ${e.message}")
        }
    }

    fun setThemeFromPreferences(appPreferences: SharedPreferences, activity: AppCompatActivity) {
        val theme = appPreferences.getInt("theme", 3)
        when (theme) {
            1 -> activity.setTheme(R.style.AppTheme)
            2 -> activity.setTheme(R.style.AppThemeDark)
            3 -> {
                val nightModeFlags = activity.resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
                if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
                    activity.setTheme(R.style.AppThemeDark)
                } else {
                    activity.setTheme(R.style.AppTheme)
                }
            }
        }
    }
    private fun registrarUsuario() {
        val nombreInput = nombre.text.toString().trim()
        val correoInput = correo.text.toString().trim()
        val contraInput = contra.text.toString().trim()
        val edadInput = edad.text.toString().trim()
        val pesoInput = peso.text.toString().trim()
        val alturaInput = altura.text.toString().trim()

        if (nombreInput.isEmpty() || correoInput.isEmpty() || contraInput.isEmpty() ||
            edadInput.isEmpty() || pesoInput.isEmpty() || alturaInput.isEmpty()
        ) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear usuario en Firebase Authentication
        auth.createUserWithEmailAndPassword(correoInput, contraInput)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val usuarioId = auth.currentUser?.uid
                    if (usuarioId != null) {
                        guardarDatosEnFirestore(
                            usuarioId,
                            nombreInput,
                            correoInput,
                            contraInput,
                            edadInput.toInt(),
                            pesoInput.toFloat(),
                            alturaInput.toFloat()
                        )
                    }
                } else {
                    Toast.makeText(this, "Error al registrar usuario: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun guardarDatosEnFirestore(
        userId: String,
        nombre: String,
        correo: String,
        contrasena: String,
        edad: Int,
        peso: Float,
        altura: Float
    ) {
        val cliente = Cliente(
            cNombre = nombre,
            cCorreo = correo,
            cContrasena = contrasena,
            cEdad = edad,
            cPeso = peso,
            cAltura = altura,
            cRecordar = false,
            cRutinas = arrayListOf()
        )

        db.collection("Usuarios").document(correo)
            .set(cliente)
            .addOnSuccessListener {
                Toast.makeText(this, "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show()
                irALogin()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al guardar datos en Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun irALogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
