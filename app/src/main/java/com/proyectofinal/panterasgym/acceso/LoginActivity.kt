package com.proyectofinal.panterasgym.acceso

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.proyectofinal.panterasgym.MenuActivity
import com.proyectofinal.panterasgym.R
import com.proyectofinal.panterasgym.clases.Cliente
import com.proyectofinal.panterasgym.clases.Rutina

class LoginActivity : AppCompatActivity() {
    private lateinit var objCliente: Cliente
    private val objRutinas: ArrayList<Rutina> = ArrayList()

    private lateinit var correo: EditText
    private lateinit var contra: EditText

    private lateinit var recordar: Switch

    private lateinit var ingresar: Button
    private lateinit var registrarse: TextView
    /*
    private lateinit var textInputLayout: TextInputLayout
    private lateinit var textInputLayout2: TextInputLayout

    private lateinit var ingresar: Button
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        // Carga la preferencia del tema.
        val sharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE)
        val darkTheme = sharedPreferences.getBoolean("darkTheme", false)

        // Aplica el tema correcto.
        if (darkTheme) {
            setTheme(R.style.AppTheme_Dark)
        } else {
            setTheme(R.style.AppTheme)
        }

         */

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        objCliente = Cliente()

        correo = findViewById(R.id.edtCorreo)
        contra = findViewById(R.id.edtContrasena)

        recordar = findViewById(R.id.stRecordar)

        ingresar = findViewById(R.id.btnIngresar)
        registrarse = findViewById(R.id.lblRegistrarse)

        var infoRecibida = intent.extras
        if (infoRecibida != null) {
            objCliente.cNombre = infoRecibida.getString("cNombre")!!
            objCliente.cCorreo = infoRecibida.getString("cCorreo")!!
            objCliente.cContrasena = infoRecibida.getString("cContrasena")!!
            objCliente.cEdad = infoRecibida.getInt("cEdad")!!
            objCliente.cPeso = infoRecibida.getFloat("cPeso")!!
            objCliente.cAltura = infoRecibida.getFloat("cAltura")!!
            objCliente.cRecordar = infoRecibida.getBoolean("cRecordar")!!
            objCliente.cRutinas = intent.getSerializableExtra("cRutinas") as ArrayList<Rutina>
        }
        /*
        ingresar = findViewById(R.id.btnIngresar)
        textInputLayout = findViewById(R.id.lyt_dtx_correo)
        textInputLayout2 = findViewById(R.id.lyt_dtx_contrasena)
        val colorStateList = ContextCompat.getColorStateList(this, R.color.text_input_box_stroke)
        val colorStateListDark = ContextCompat.getColorStateList(this, R.color.text_input_box_stroke_dark)
        if (darkTheme) {
            textInputLayout.setBoxStrokeColorStateList(colorStateListDark!!)
            textInputLayout2.setBoxStrokeColorStateList(colorStateListDark!!)
        } else {
            textInputLayout.setBoxStrokeColorStateList(colorStateList!!)
            textInputLayout2.setBoxStrokeColorStateList(colorStateList!!)
        }

         */
        eventosClic()
    }

    private fun eventosClic() {
        var intent: Intent?
        /*
        ingresar.setOnClickListener {
            Toast.makeText(this, "Favor de llenar todos los campos.", Toast.LENGTH_SHORT).show()

            // Cambia la preferencia del tema.
            val sharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("darkTheme", !sharedPreferences.getBoolean("darkTheme", false))
            editor.apply()

            // Recrea la actividad para que los cambios se apliquen.
            recreate()
        }
         */
        ingresar.setOnClickListener {
            if (correo.text.isNotBlank() && contra.text.isNotBlank()) {
                if (correo.text.toString().equals(objCliente.cCorreo)) {
                    if (contra.text.toString().equals(objCliente.cContrasena)) {
                        if (recordar.isChecked) {
                            objCliente?.cRecordar = true
                            guardarUsuario(objCliente)
                        }
                        val intent = Intent(applicationContext, MenuActivity::class.java)
                        intent.putExtra("cNombre", objCliente.cNombre)
                        intent.putExtra("cCorreo", objCliente.cCorreo)
                        intent.putExtra("cContrasena", objCliente.cContrasena)
                        intent.putExtra("cEdad", objCliente.cEdad)
                        intent.putExtra("cPeso", objCliente.cPeso)
                        intent.putExtra("cAltura", objCliente.cAltura)
                        intent.putExtra("cRecordar", objCliente.cRecordar)
                        intent.putExtra("cRutinas", ArrayList(objRutinas))
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(this, "Correo incorrecto.", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "Favor de completar los campos.", Toast.LENGTH_SHORT).show()
            }
        }
        registrarse.setOnClickListener {
            intent = Intent(applicationContext, RegistroActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun guardarUsuario(objCliente: Cliente) {
        val preferences: SharedPreferences = getSharedPreferences("Datos usuario", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString("cNombre", objCliente.cNombre)
        editor.putString("cCorreo", objCliente.cCorreo)
        editor.putString("cContra", objCliente.cContrasena)
        editor.putInt("cEdad", objCliente.cEdad)
        editor.putFloat("cPeso", objCliente.cPeso)
        editor.putFloat("cAltura", objCliente.cAltura)
        editor.putBoolean("cRecordar", objCliente.cRecordar)
        val gson = Gson()
        val rutinasJson = gson.toJson(objCliente.cRutinas)
        editor.putString("cRutinas", rutinasJson)
        editor.apply()
    }
}
