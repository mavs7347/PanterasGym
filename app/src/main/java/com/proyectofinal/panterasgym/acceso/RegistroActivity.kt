package com.proyectofinal.panterasgym.acceso

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.proyectofinal.panterasgym.MenuActivity
import com.proyectofinal.panterasgym.R
import com.proyectofinal.panterasgym.clases.Cliente
import com.proyectofinal.panterasgym.clases.Rutina

class RegistroActivity : AppCompatActivity() {
    private lateinit var objCliente: Cliente
    private val objRutinas: ArrayList<Rutina> = ArrayList()

    private lateinit var nombre: EditText
    private lateinit var correo: EditText
    private lateinit var contra: EditText
    private lateinit var edad: EditText
    private lateinit var peso: EditText
    private lateinit var altura: EditText

    private lateinit var registrar: Button
    private lateinit var regresar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        objCliente = Cliente()

        nombre = findViewById(R.id.edtNombre)
        correo = findViewById(R.id.edtCorreo)
        contra = findViewById(R.id.edtContrasena)
        edad = findViewById(R.id.edtEdad)
        peso = findViewById(R.id.edtPeso)
        altura = findViewById(R.id.edtAltura)

        registrar = findViewById(R.id.btnRegistrar)
        regresar = findViewById(R.id.lblRegresar)

        eventosClic()
    }

    private fun eventosClic() {
        registrar.setOnClickListener {
            if (nombre.text.isNotEmpty() && correo.text.isNotEmpty() && contra.text.isNotEmpty() && edad.text.isNotEmpty() && peso.text.isNotEmpty() && altura.text.isNotEmpty()) {
                registrarDatos()
            }
            else {
                Toast.makeText(this, "Favor de completar los campos.", Toast.LENGTH_LONG).show()
            }
        }
        regresar.setOnClickListener {
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registrarDatos() {
        try {
            objCliente?.cNombre = nombre.text.toString()
            objCliente?.cCorreo = correo.text.toString()
            objCliente?.cContrasena = contra.text.toString()
            objCliente?.cEdad = edad.text.toString().toInt()
            objCliente?.cPeso = peso.text.toString().toFloat()
            objCliente?.cAltura = altura.text.toString().toFloat()
            objCliente?.cRecordar = false
            objCliente?.cRutinas = objRutinas

            Toast.makeText(this, "Usuario registrado.", Toast.LENGTH_SHORT).show()
            limpiarCampos()

            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.putExtra("cNombre", objCliente.cNombre)
            intent.putExtra("cCorreo", objCliente.cCorreo)
            intent.putExtra("cContrasena", objCliente.cContrasena)
            intent.putExtra("cEdad", objCliente.cEdad)
            intent.putExtra("cPeso", objCliente.cPeso)
            intent.putExtra("cAltura", objCliente.cAltura)
            intent.putExtra("cRecordar", objCliente.cRecordar)
            intent.putExtra("cRutinas", ArrayList(objCliente.cRutinas))
            startActivity(intent)
            finish()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "No se pudieron guardar los datos.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        nombre.text.clear()
        nombre.clearFocus()
        correo.text.clear()
        correo.clearFocus()
        contra.text.clear()
        contra.clearFocus()
        edad.text.clear()
        edad.clearFocus()
        peso.text.clear()
        peso.clearFocus()
        altura.text.clear()
        altura.clearFocus()
    }
}