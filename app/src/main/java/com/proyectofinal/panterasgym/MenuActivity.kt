package com.proyectofinal.panterasgym

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.proyectofinal.panterasgym.acceso.LoginActivity
import com.proyectofinal.panterasgym.clases.Cliente
import com.proyectofinal.panterasgym.clases.Rutina
import com.proyectofinal.panterasgym.databinding.ActivityMenuBinding
import com.proyectofinal.panterasgym.opciones.CalculadoraFragment
import com.proyectofinal.panterasgym.opciones.PantallaPrincipalFragment
import com.proyectofinal.panterasgym.opciones.ProgresoFragment
import com.proyectofinal.panterasgym.opciones.RutinasPersonalesFragment
import com.proyectofinal.panterasgym.opciones.RutinasRecomendadasFragment
import com.proyectofinal.panterasgym.opciones.TemaFragment

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var clienteViewModel: ClienteViewModel

    private lateinit var objCliente: Cliente

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val appPreferences: SharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        if (appPreferences.contains("theme")) {
            //Toast.makeText(this, "${appPreferences.getInt("theme", 0)}", Toast.LENGTH_LONG).show()
            if (appPreferences.getInt("theme", 0) == 1) {
                setTheme(R.style.AppTheme)
            } else if (appPreferences.getInt("theme", 0) == 2) {
                setTheme(R.style.AppThemeDark)
            } else if (appPreferences.getInt("theme", 0) == 3) {
                val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                when (nightModeFlags) {
                    Configuration.UI_MODE_NIGHT_YES -> setTheme(R.style.AppThemeDark)
                    Configuration.UI_MODE_NIGHT_NO, Configuration.UI_MODE_NIGHT_UNDEFINED -> setTheme(R.style.AppTheme)
                }
            }
        } else {
            val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            when (nightModeFlags) {
                Configuration.UI_MODE_NIGHT_YES -> setTheme(R.style.AppThemeDark)
                Configuration.UI_MODE_NIGHT_NO, Configuration.UI_MODE_NIGHT_UNDEFINED -> setTheme(R.style.AppTheme)
            }
        }
        super.onCreate(savedInstanceState)
        /*
        val sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        if (sharedPreferences.getBoolean("isChangingTheme", false)) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_tema, TemaFragment())
            transaction.commit()

            val editor = sharedPreferences.edit()
            editor.putBoolean("isChangingTheme", false)
            editor.apply()
        }
        */


        clienteViewModel = ViewModelProvider(this).get(ClienteViewModel::class.java)
        clienteViewModel.cliente.observe(this, Observer { cliente ->
            objCliente = cliente
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
        })

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMenu.toolbar)

        binding.appBarMenu.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_pantalla_principal, R.id.nav_rutinas_personales, R.id.nav_rutinas_recomendadas, R.id.nav_progreso, R.id.nav_calculadora, R.id.nav_tema
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Set this Activity as the OnNavigationItemSelectedListener
        navView.setNavigationItemSelectedListener(this)

        objCliente = Cliente()
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
        else {
            val prefs = getSharedPreferences("Datos usuario", MODE_PRIVATE)
            objCliente.cNombre = prefs.getString("cNombre", "")!!
            objCliente.cCorreo = prefs.getString("cCorreo", "")!!
            objCliente.cContrasena = prefs.getString("cContra", "")!!
            objCliente.cEdad = prefs.getInt("cEdad", 0)!!
            objCliente.cPeso = prefs.getFloat("cPeso", 0.0f)!!
            objCliente.cAltura = prefs.getFloat("cAltura", 0.0f)!!
            objCliente.cRecordar = prefs.getBoolean("cRecordar", false)!!
            val gson = Gson()
            val rutinasJson = prefs.getString("cRutinas", "")!!
            val type = object : TypeToken<List<Rutina>>() {}.type
            objCliente.cRutinas = gson.fromJson(rutinasJson, type)!!
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent?
        when (item.itemId) {
            R.id.action_cerrar_sesion -> {
                Toast.makeText(this, "Cerrando sesión.", Toast.LENGTH_SHORT).show()
                cerrarSesion()

                intent = Intent(applicationContext, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.putExtra("cNombre", objCliente.cNombre)
                intent.putExtra("cCorreo", objCliente.cCorreo)
                intent.putExtra("cContrasena", objCliente.cContrasena)
                intent.putExtra("cEdad", objCliente.cEdad)
                intent.putExtra("cPeso", objCliente.cPeso)
                intent.putExtra("cAltura", objCliente.cAltura)
                intent.putExtra("cRecordar", false)
                intent.putExtra("cRutinas", ArrayList(objCliente.cRutinas))
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun cerrarSesion() {
        val preferences: SharedPreferences = getSharedPreferences("Datos usuario", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        val fragment: Fragment
        when (id) {
            R.id.nav_pantalla_principal -> {
                fragment = PantallaPrincipalFragment()
            }
            R.id.nav_rutinas_personales -> {
                fragment = RutinasPersonalesFragment()
            }
            R.id.nav_rutinas_recomendadas -> {
                fragment = RutinasRecomendadasFragment()
            }
            R.id.nav_progreso -> {
                fragment = ProgresoFragment()
            }
            R.id.nav_calculadora -> {
                fragment = CalculadoraFragment()
            }
            R.id.nav_tema -> {
                fragment = TemaFragment()
            }
            else -> {
                return false
            }
        }

        val bundle = Bundle()
        bundle.putString("cNombre", objCliente.cNombre)
        bundle.putString("cCorreo", objCliente.cCorreo)
        bundle.putString("cContrasena", objCliente.cContrasena)
        bundle.putInt("cEdad", objCliente.cEdad)
        bundle.putFloat("cPeso", objCliente.cPeso)
        bundle.putFloat("cAltura", objCliente.cAltura)
        bundle.putBoolean("cRecordar", objCliente.cRecordar)
        bundle.putSerializable("cRutinas", objCliente.cRutinas)
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_content_menu, fragment)
            .commit()

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}



/*
package com.proyectofinal.panterasgym

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.proyectofinal.panterasgym.acceso.LoginActivity
import com.proyectofinal.panterasgym.clases.Cliente
import com.proyectofinal.panterasgym.clases.Rutina
import com.proyectofinal.panterasgym.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var objCliente: Cliente

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMenu.toolbar)

        binding.appBarMenu.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_pantalla_principal, R.id.nav_rutinas_personales, R.id.nav_rutinas_recomendadas, R.id.nav_progreso, R.id.nav_calculadora, R.id.nav_tema
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        objCliente = Cliente()
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
        else {
            val prefs = getSharedPreferences("Datos usuario", MODE_PRIVATE)
            objCliente.cNombre = prefs.getString("cNombre", "")!!
            objCliente.cCorreo = prefs.getString("cCorreo", "")!!
            objCliente.cContrasena = prefs.getString("cContra", "")!!
            objCliente.cEdad = prefs.getInt("cEdad", 0)!!
            objCliente.cPeso = prefs.getFloat("cPeso", 0.0f)!!
            objCliente.cAltura = prefs.getFloat("cAltura", 0.0f)!!
            objCliente.cRecordar = prefs.getBoolean("cRecordar", false)!!
            val gson = Gson()
            val rutinasJson = prefs.getString("cRutinas", "")
            val type = object : TypeToken<List<Rutina>>() {}.type
            objCliente.cRutinas = gson.fromJson(rutinasJson, type)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent?
        when (item.itemId) {
            R.id.action_cerrar_sesion -> {
                Toast.makeText(this, "Cerrando sesión.", Toast.LENGTH_SHORT).show()
                cerrarSesion()

                intent = Intent(applicationContext, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.putExtra("cNombre", objCliente.cNombre)
                intent.putExtra("cCorreo", objCliente.cCorreo)
                intent.putExtra("cContrasena", objCliente.cContrasena)
                intent.putExtra("cEdad", objCliente.cEdad)
                intent.putExtra("cPeso", objCliente.cPeso)
                intent.putExtra("cAltura", objCliente.cAltura)
                intent.putExtra("cRecordar", false)
                intent.putExtra("cRutinas", ArrayList(objCliente.cRutinas))
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun cerrarSesion() {
        val preferences: SharedPreferences = getSharedPreferences("preferenciasUsuario", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {

    }
}

 */