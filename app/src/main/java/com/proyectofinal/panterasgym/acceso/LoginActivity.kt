package com.proyectofinal.panterasgym.acceso

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.proyectofinal.panterasgym.MenuActivity
import com.proyectofinal.panterasgym.R
import com.proyectofinal.panterasgym.clases.Cliente

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var db: FirebaseFirestore

    private lateinit var correo: EditText
    private lateinit var contra: EditText
    private lateinit var recordar: Switch
    private lateinit var ingresar: Button
    private lateinit var registrarse: TextView
    private lateinit var googleSignInButton: Button

    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar Firebase Authentication y Firestore
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        configurarGoogleSignIn()

        // Verificar sesión existente
        verificarSesion()

        // Configurar layout
        setContentView(R.layout.activity_login)

        correo = findViewById(R.id.edtCorreo)
        contra = findViewById(R.id.edtContrasena)
        recordar = findViewById(R.id.stRecordar)
        ingresar = findViewById(R.id.btnIngresar)
        registrarse = findViewById(R.id.lblRegistrarse)
        googleSignInButton = findViewById(R.id.btnGoogleSignIn)

        ingresar.setOnClickListener { autenticarConCorreo() }
        googleSignInButton.setOnClickListener { autenticarConGoogle() }
        registrarse.setOnClickListener { abrirRegistro() }
    }

    private fun configurarGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun verificarSesion() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val clienteRef = db.collection("Usuarios").document(currentUser.email!!)
            clienteRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val cliente = document.toObject(Cliente::class.java)
                        if (cliente?.cRecordar == true) {
                            abrirMenuConCliente(cliente)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al verificar sesión: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun autenticarConCorreo() {
        val email = correo.text.toString().trim()
        val password = contra.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val usuario = auth.currentUser
                    if (usuario != null) {
                        actualizarRecordar(usuario.email!!, recordar.isChecked)
                    }
                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun autenticarConGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount = task.getResult(Exception::class.java)!!
                firebaseAuthConGoogle(account.idToken!!)
            } catch (e: Exception) {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthConGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val usuario = auth.currentUser
                    if (usuario != null) {
                        consultarOCrearUsuarioConRecordar(
                            email = usuario.email ?: "",
                            displayName = usuario.displayName ?: "Sin nombre",
                            recordar = recordar.isChecked // Leer el estado del switch
                        )
                    }
                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun consultarOCrearUsuarioConRecordar(email: String, displayName: String, recordar: Boolean) {
        val clienteRef = db.collection("Usuarios").document(email)

        clienteRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val cliente = document.toObject(Cliente::class.java)
                    if (cliente != null) {
                        // Actualizar cRecordar si es necesario
                        clienteRef.update("cRecordar", recordar)
                            .addOnSuccessListener { abrirMenuConCliente(cliente) }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error al actualizar preferencia: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    // Crear un nuevo cliente con cRecordar
                    val nuevoCliente = Cliente(
                        cNombre = displayName,
                        cCorreo = email,
                        cContrasena = "", // Contraseña vacía para usuarios de Google
                        cEdad = 0,
                        cPeso = 0f,
                        cAltura = 0f,
                        cRecordar = recordar,
                        cRutinas = arrayListOf()
                    )
                    clienteRef.set(nuevoCliente)
                        .addOnSuccessListener { abrirMenuConCliente(nuevoCliente) }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error al crear usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al consultar usuario: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun consultarOCrearUsuario(email: String, displayName: String) {
        val clienteRef = db.collection("Usuarios").document(email)

        clienteRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val cliente = document.toObject(Cliente::class.java)
                    if (cliente != null) {
                        // Validar rutinas (asegurar que siempre exista al menos un arreglo vacío)
                        if (cliente.cRutinas == null) {
                            clienteRef.update("cRutinas", arrayListOf<String>())
                                .addOnSuccessListener {
                                    abrirMenuConCliente(cliente)
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Error al actualizar rutinas: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            abrirMenuConCliente(cliente)
                        }
                    }
                } else {
                    // Crear un nuevo cliente con valores iniciales
                    val nuevoCliente = Cliente(
                        cNombre = displayName,
                        cCorreo = email,
                        cContrasena = "", // Contraseña vacía para usuarios de Google
                        cEdad = 0,
                        cPeso = 0f,
                        cAltura = 0f,
                        cRecordar = true,
                        cRutinas = arrayListOf() // Inicializar rutinas vacías
                    )
                    clienteRef.set(nuevoCliente)
                        .addOnSuccessListener {
                            abrirMenuConCliente(nuevoCliente)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error al crear usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al consultar usuario: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun actualizarRecordar(email: String, recordar: Boolean) {
        val clienteRef = db.collection("Usuarios").document(email)

        clienteRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Actualiza si el documento ya existe
                    clienteRef.update("cRecordar", recordar)
                        .addOnSuccessListener {
                            clienteRef.get()
                                .addOnSuccessListener { doc ->
                                    val cliente = doc.toObject(Cliente::class.java)
                                    if (cliente != null) abrirMenuConCliente(cliente)
                                }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error al actualizar preferencia: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    // Crea el documento si no existe
                    val nuevoCliente = Cliente(
                        cNombre = "Nuevo Usuario", // Cambiar por un valor adecuado
                        cCorreo = email,
                        cContrasena = "", // No necesario si es solo para preferencias
                        cEdad = 0,
                        cPeso = 0f,
                        cAltura = 0f,
                        cRecordar = recordar,
                        cRutinas = arrayListOf()
                    )
                    clienteRef.set(nuevoCliente)
                        .addOnSuccessListener {
                            abrirMenuConCliente(nuevoCliente)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error al crear usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al consultar documento: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun abrirMenuConCliente(cliente: Cliente) {
        val intent = Intent(this, MenuActivity::class.java).apply {
            putExtra("cliente", cliente)
        }
        startActivity(intent)
        finish()
    }

    private fun abrirRegistro() {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
        finish()
    }
}
