package com.proyectofinal.panterasgym

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.proyectofinal.panterasgym.acceso.LoginActivity
import com.proyectofinal.panterasgym.clases.Cliente
import com.proyectofinal.panterasgym.databinding.ActivityMenuBinding
import com.proyectofinal.panterasgym.opciones.CalculadoraFragment
import com.proyectofinal.panterasgym.opciones.PantallaPrincipalFragment
import com.proyectofinal.panterasgym.opciones.ProgresoFragment
import com.proyectofinal.panterasgym.opciones.personalizadas.RutinasFragment
import com.proyectofinal.panterasgym.opciones.RutinasRecomendadasFragment
import com.proyectofinal.panterasgym.opciones.TemaFragment

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var clienteViewModel: ClienteViewModel
    private lateinit var objCliente: Cliente
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuBinding
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Configuración de tema
        val appPreferences: SharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        setThemeFromPreferences(appPreferences)

        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMenu.toolbar)

        clienteViewModel = ViewModelProvider(this).get(ClienteViewModel::class.java)

        // Cargar datos iniciales
        cargarDatosCliente()

        // Observa los cambios en el cliente y actualiza Firestore
        clienteViewModel.cliente.observe(this) { cliente ->
            objCliente = cliente
            guardarClienteEnPreferences(objCliente)
            actualizarClienteEnFirestore(objCliente)
        }

        // Configuración de Navigation Drawer
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_menu)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_pantalla_principal, R.id.nav_rutinas_personales, R.id.nav_rutinas_recomendadas,
                R.id.nav_progreso, R.id.nav_calculadora, R.id.nav_tema
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
    }

    private fun setThemeFromPreferences(appPreferences: SharedPreferences) {
        try {
            val theme = appPreferences.getInt("theme", 0)
            when (theme) {
                1 -> {
                    android.util.Log.d("MenuActivity", "Aplicando tema claro")
                    setTheme(R.style.AppTheme)
                }
                2 -> {
                    android.util.Log.d("MenuActivity", "Aplicando tema oscuro")
                    setTheme(R.style.AppThemeDark)
                }
                3 -> {
                    val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                    if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                        android.util.Log.d("MenuActivity", "Aplicando tema oscuro (predeterminado)")
                        setTheme(R.style.AppThemeDark)
                    } else {
                        android.util.Log.d("MenuActivity", "Aplicando tema claro (predeterminado)")
                        setTheme(R.style.AppTheme)
                    }
                }
                else -> {
                    android.util.Log.e("MenuActivity", "Valor desconocido de tema: $theme")
                    setTheme(R.style.AppTheme)
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("MenuActivity", "Error al configurar tema: ${e.message}")
        }
    }

    private fun cargarDatosCliente() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val clienteRef = db.collection("Usuarios").document(currentUser.email!!)

            clienteRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Cargar datos existentes del cliente
                        val cliente = document.toObject(Cliente::class.java)
                        clienteViewModel.cliente.value = cliente
                    } else {
                        // Solo crear un nuevo cliente si no existe
                        val nuevoCliente = Cliente(
                            cNombre = currentUser.displayName ?: "Sin nombre",
                            cCorreo = currentUser.email ?: "Sin correo",
                            cContrasena = "", // No disponible si es Google Sign-In
                            cEdad = 0,
                            cPeso = 0f,
                            cAltura = 0f,
                            cRecordar = true,
                            cRutinas = arrayListOf()
                        )
                        clienteViewModel.cliente.value = nuevoCliente
                        clienteRef.set(nuevoCliente)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Usuario registrado en Firestore.", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error al registrar usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al cargar datos: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun guardarClienteEnPreferences(cliente: Cliente) {
        val preferences: SharedPreferences = getSharedPreferences("Datos usuario", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString("cNombre", cliente.cNombre)
        editor.putString("cCorreo", cliente.cCorreo)
        editor.putString("cContra", cliente.cContrasena)
        editor.putInt("cEdad", cliente.cEdad)
        editor.putFloat("cPeso", cliente.cPeso)
        editor.putFloat("cAltura", cliente.cAltura)
        editor.putBoolean("cRecordar", cliente.cRecordar)
        val gson = Gson()
        editor.putString("cRutinas", gson.toJson(cliente.cRutinas))
        editor.apply()
    }

    private fun actualizarClienteEnFirestore(cliente: Cliente) {
        db.collection("Usuarios").document(cliente.cCorreo)
            .set(cliente)
            .addOnSuccessListener {
                Toast.makeText(this, "Datos actualizados en Firestore.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al actualizar datos en Firestore.", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_cerrar_sesion) {
            cerrarSesion()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun cerrarSesion() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
        finish()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment = when (item.itemId) {
            R.id.nav_pantalla_principal -> PantallaPrincipalFragment()
            R.id.nav_rutinas_personales -> RutinasFragment()
            R.id.nav_rutinas_recomendadas -> RutinasRecomendadasFragment()
            R.id.nav_progreso -> ProgresoFragment()
            R.id.nav_calculadora -> CalculadoraFragment()
            R.id.nav_tema -> TemaFragment()
            else -> return false
        }

        // Crear el Bundle con los datos del cliente
        val bundle = Bundle().apply {
            putString("cNombre", objCliente.cNombre)
            putString("cCorreo", objCliente.cCorreo)
            putString("cContrasena", objCliente.cContrasena)
            putInt("cEdad", objCliente.cEdad)
            putFloat("cPeso", objCliente.cPeso)
            putFloat("cAltura", objCliente.cAltura)
            putBoolean("cRecordar", objCliente.cRecordar)
            putSerializable("cRutinas", objCliente.cRutinas)
        }

        // Establecer los argumentos en el fragmento
        fragment.arguments = bundle

        // Reemplazar el fragmento en el contenedor
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_content_menu, fragment)
            .commit()

        // Cerrar el drawer
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
