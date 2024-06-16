package com.example.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.app.AlertDialog

class RutinasFragment : Fragment(), RutinasAdapter.RutinaClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var rutinasAdapter: RutinasAdapter
    private lateinit var rutinasList: MutableList<Rutina>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rutinas, container, false)

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Inicializar lista de rutinas (puedes cargar desde una base de datos aquí)
        rutinasList = mutableListOf()
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
    }

    // Método para eliminar una rutina
    fun eliminarRutina(position: Int) {
        rutinasList.removeAt(position)
        rutinasAdapter.notifyDataSetChanged()
    }

    // Método para mostrar el formulario de modificar rutina
    private fun mostrarFormularioModificar(position: Int) {
        val rutina = rutinasList[position]
        val formularioFragment = FormularioFragment.newInstance(
            rutina.nombre,
            rutina.descripcion,
            rutina.fechaCreacion,
            rutina.duracion,
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
            .setTitle("Ejercicios de ${rutina.nombre}")
            .setPositiveButton("Aceptar", null) // Aquí puedes agregar lógica adicional si lo necesitas
            .create()

        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_ver_ejercicios, null)
        val ejerciciosLayout = dialogView.findViewById<LinearLayout>(R.id.ll_ejercicios)

        for (ejercicio in rutina.rEjercicios) {
            val ejercicioView = inflater.inflate(R.layout.item_ejercicio, null)
            val tvNombreEjercicio = ejercicioView.findViewById<TextView>(R.id.tv_nombre_ejercicio)
            val tvRepeticiones = ejercicioView.findViewById<TextView>(R.id.tv_repeticiones)
            val tvSeries = ejercicioView.findViewById<TextView>(R.id.tv_series)

            tvNombreEjercicio.text = ejercicio.nombre
            tvRepeticiones.text = "Repeticiones: ${ejercicio.repeticiones}"
            tvSeries.text = "Series: ${ejercicio.series}"

            ejerciciosLayout.addView(ejercicioView)
        }

        ejerciciosDialog.setView(dialogView)
        ejerciciosDialog.show()
    }
    // Método para actualizar una rutina
    fun actualizarRutina(position: Int, nombre: String, descripcion: String, fechaCreacion: String, duracion: String, rEjercicios: List<Ejercicio>) {
        rutinasList[position] = Rutina(nombre, descripcion, fechaCreacion, duracion, rEjercicios)
        rutinasAdapter.notifyDataSetChanged()
    }
}
