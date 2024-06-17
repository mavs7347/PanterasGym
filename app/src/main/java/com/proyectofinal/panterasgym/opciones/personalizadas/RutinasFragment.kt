package com.proyectofinal.panterasgym.opciones.personalizadas

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.proyectofinal.panterasgym.ClienteViewModel
import com.proyectofinal.panterasgym.R
import com.proyectofinal.panterasgym.clases.Cliente
import com.proyectofinal.panterasgym.clases.Rutina
import com.proyectofinal.panterasgym.clases.Ejercicio

class RutinasFragment : Fragment(), RutinasAdapter.RutinaClickListener {
    private lateinit var clienteViewModel: ClienteViewModel
    private val objCliente: Cliente = Cliente()
    private lateinit var recyclerView: RecyclerView
    private lateinit var rutinasAdapter: RutinasAdapter
    private lateinit var rutinasList: MutableList<Rutina>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clienteViewModel = ViewModelProvider(requireActivity()).get(ClienteViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        objCliente.cNombre = arguments?.getString("cNombre")!!
        objCliente.cCorreo = arguments?.getString("cCorreo")!!
        objCliente.cContrasena = arguments?.getString("cContrasena")!!
        objCliente.cEdad = arguments?.getInt("cEdad")!!
        objCliente.cPeso = arguments?.getFloat("cPeso")!!
        objCliente.cAltura = arguments?.getFloat("cAltura")!!
        objCliente.cRecordar = arguments?.getBoolean("cRecordar")!!
        objCliente.cRutinas = arguments?.getSerializable("cRutinas") as ArrayList<Rutina>

        val view = inflater.inflate(R.layout.fragment_rutinas, container, false)

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Inicializar lista de rutinas (puedes cargar desde una base de datos aquí)
        rutinasList = objCliente.cRutinas.toMutableList()
        rutinasAdapter = RutinasAdapter(rutinasList, this)
        recyclerView.adapter = rutinasAdapter

        // Manejar clic en el botón agregar rutina
        val agregarButton = view.findViewById<Button>(R.id.btn_agregar_rutina)
        agregarButton.setOnClickListener {
            // Mostrar formulario para agregar rutina
            mostrarFormulario()
        }

        return view
    }

    private fun mostrarFormulario() {
        val formularioFragment = FormularioFragment()
        formularioFragment.setTargetFragment(this, 1)
        formularioFragment.show(parentFragmentManager, "formulario")
    }

    override fun onEliminarClick(position: Int) {
        eliminarRutina(position)
    }

    override fun onModificarClick(position: Int) {
        mostrarFormularioModificar(position)
    }

    override fun onVerEjerciciosClick(position: Int) {
        mostrarEjercicios(position)
    }

    // Método para agregar una nueva rutina desde el formulario
    fun agregarRutina(rutina: Rutina) {
        rutinasList.add(rutina)
        rutinasAdapter.notifyDataSetChanged()
        objCliente.cRutinas.add(rutina)
        actualizarCliente()
    }

//    // Método para eliminar una rutina
//    fun eliminarRutina(position: Int) {
//        rutinasList.removeAt(position)
//        rutinasAdapter.notifyDataSetChanged()
//    }

    // Método para eliminar una rutina
    fun eliminarRutina(position: Int) {
        rutinasList.removeAt(position)
        rutinasAdapter.notifyDataSetChanged()
        objCliente.cRutinas.removeAt(position) // Eliminar la rutina del cliente
        actualizarCliente() // Actualizar el ViewModel con los cambios
    }

    // Método para actualizar una rutina
    fun actualizarRutina(position: Int, nombre: String, descripcion: String, fechaCreacion: String, duracion: String, ejercicios: List<Ejercicio>) {
        val rutina = rutinasList[position]
        rutina.rNombre = nombre
        rutina.rDescripcion = descripcion
        rutina.rCreacion = fechaCreacion
        rutina.rDuracion = duracion
        rutina.rEjercicios = ejercicios

        rutinasAdapter.notifyItemChanged(position)
        objCliente.cRutinas[position] = rutina // Actualizar la rutina en el cliente
        actualizarCliente() // Actualizar el ViewModel con los cambios
    }

    // Método para mostrar el formulario de modificar rutina
    private fun mostrarFormularioModificar(position: Int) {
        val rutina = rutinasList[position]
        val formularioFragment = FormularioFragment.newInstance(
            rutina.rNombre,
            rutina.rDescripcion,
            rutina.rCreacion,
            rutina.rDuracion,
            rutina.rEjercicios.toMutableList(), // Pasar la lista de ejercicios
            position
        )
        formularioFragment.setTargetFragment(this, 1)
        formularioFragment.show(parentFragmentManager, "formulario_modificar")
    }

    // Método para mostrar los ejercicios de una rutina
    private fun mostrarEjercicios(position: Int) {
        val rutina = rutinasList[position]
        val ejerciciosDialog = AlertDialog.Builder(requireContext())
            .setTitle("Ejercicios de ${rutina.rNombre}")
            .setPositiveButton("Aceptar", null) // Aquí puedes agregar lógica adicional si lo necesitas
            .create()

        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_ver_ejercicios, null)
        val ejerciciosLayout = dialogView.findViewById<LinearLayout>(R.id.ll_ejercicios)

        for (ejercicio in rutina.rEjercicios) {
            val ejercicioView = inflater.inflate(R.layout.item_ejercicio, null)
            val tvNombreEjercicio = ejercicioView.findViewById<TextView>(R.id.tv_nombre_ejercicio)
            val tvDescripcionEjercicio = ejercicioView.findViewById<TextView>(R.id.tv_descripcion_ejercicio)
            val tvRepeticiones = ejercicioView.findViewById<TextView>(R.id.tv_repeticiones)
            val tvPeso = ejercicioView.findViewById<TextView>(R.id.tv_peso)
            val tvSeries = ejercicioView.findViewById<TextView>(R.id.tv_series)

            tvNombreEjercicio.text = ejercicio.eNombre
            tvDescripcionEjercicio.text = "Descripcion: ${ejercicio.eDescripcion}"
            tvRepeticiones.text = "Repeticiones: ${ejercicio.eRepeticiones}"
            tvPeso.text = "Peso: ${ejercicio.ePeso} kg"
            tvSeries.text = "Series: ${ejercicio.eSeries}"

            ejerciciosLayout.addView(ejercicioView)
        }

        ejerciciosDialog.setView(dialogView)
        ejerciciosDialog.show()
    }

    private fun actualizarCliente() {
        clienteViewModel.cliente.value = objCliente
    }
}