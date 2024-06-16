package com.proyectofinal.panterasgym.opciones.personalizadas

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.app.AlertDialog
import java.util.ArrayList
import com.proyectofinal.panterasgym.R
import com.proyectofinal.panterasgym.clases.Ejercicio
import com.proyectofinal.panterasgym.clases.Rutina
class FormularioFragment : DialogFragment() {

    private var nombreRutina: String? = null
    private var descripcionRutina: String? = null
    private var fechaCreacionRutina: String? = null
    private var duracionRutina: String? = null
    private var rEjercicios: MutableList<Ejercicio> = mutableListOf()
    private var position: Int? = null

    companion object {
        private const val ARG_NOMBRE = "nombre"
        private const val ARG_DESCRIPCION = "descripcion"
        private const val ARG_FECHA_CREACION = "fecha_creacion"
        private const val ARG_DURACION = "duracion"
        private const val ARG_POSITION = "position"
        private const val ARG_EJERCICIOS = "ejercicios"

        fun newInstance(
            nombre: String,
            descripcion: String,
            fechaCreacion: String,
            duracion: String,
            ejercicios: MutableList<Ejercicio>?, // Cambio: aceptar ejercicios como argumento
            position: Int?
        ): FormularioFragment {
            val fragment = FormularioFragment()
            val args = Bundle()
            args.putString(ARG_NOMBRE, nombre)
            args.putString(ARG_DESCRIPCION, descripcion)
            args.putString(ARG_FECHA_CREACION, fechaCreacion)
            args.putString(ARG_DURACION, duracion)
            ejercicios?.let { args.putParcelableArrayList(ARG_EJERCICIOS, ArrayList(it)) } // Pasar la lista de ejercicios si existe
            position?.let { args.putInt(ARG_POSITION, it) }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nombreRutina = it.getString(ARG_NOMBRE)
            descripcionRutina = it.getString(ARG_DESCRIPCION)
            fechaCreacionRutina = it.getString(ARG_FECHA_CREACION)
            duracionRutina = it.getString(ARG_DURACION)
            position = it.getInt(ARG_POSITION, -1)
            rEjercicios = it.getParcelableArrayList<Ejercicio>(ARG_EJERCICIOS)?.toMutableList() ?: mutableListOf()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_formulario, container, false)

        val etNombreRutina = view.findViewById<EditText>(R.id.et_nombre_rutina)
        val etDescripcion = view.findViewById<EditText>(R.id.et_descripcion)
        val etFechaCreacion = view.findViewById<EditText>(R.id.et_fecha_creacion)
        val etDuracion = view.findViewById<EditText>(R.id.et_duracion)
        val btnAgregarEjercicio = view.findViewById<Button>(R.id.btn_agregar_ejercicio)

        etNombreRutina.setText(nombreRutina)
        etDescripcion.setText(descripcionRutina)
        etFechaCreacion.setText(fechaCreacionRutina)
        etDuracion.setText(duracionRutina)

        btnAgregarEjercicio.setOnClickListener {
            mostrarDialogoAgregarEjercicio()
        }

        // Manejar clic en el botón de agregar/modificar
        val btnAgregar = view.findViewById<Button>(R.id.btn_agregar)
        btnAgregar.setOnClickListener {
            // Obtener datos del formulario
            val nombre = etNombreRutina.text.toString()
            val descripcion = etDescripcion.text.toString()
            val fechaCreacion = etFechaCreacion.text.toString()
            val duracion = etDuracion.text.toString()

            // Validar que todos los campos estén completos
            if (nombre.isNotEmpty() && descripcion.isNotEmpty() && fechaCreacion.isNotEmpty() && duracion.isNotEmpty()) {
                val targetFragment = targetFragment as? RutinasFragment
                if (position == null) {
                    // Agregar nueva rutina
                    val rutina = Rutina(nombre, descripcion, fechaCreacion, duracion, rEjercicios)
                    targetFragment?.agregarRutina(rutina)
                } else {
                    // Actualizar rutina existente
                    targetFragment?.actualizarRutina(position!!, nombre, descripcion, fechaCreacion, duracion, rEjercicios)
                }

                // Cerrar el formulario
                dismiss()
            } else {
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun mostrarDialogoAgregarEjercicio() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_agregar_ejercicio, null)
        dialogBuilder.setView(dialogView)

        val etNombreEjercicio = dialogView.findViewById<EditText>(R.id.et_nombre_ejercicio)
        val etDescripcionEjercicio = dialogView.findViewById<EditText>(R.id.et_descripcion_ejercicio)
        val etRepeticiones = dialogView.findViewById<EditText>(R.id.et_repeticiones)
        val etPeso = dialogView.findViewById<EditText>(R.id.et_peso)
        val etSeries = dialogView.findViewById<EditText>(R.id.et_series)

        dialogBuilder.setTitle("Agregar Ejercicio")
        dialogBuilder.setPositiveButton("Agregar") { _, _ ->
            val nombreEjercicio = etNombreEjercicio.text.toString()
            val descripcionEjercicio = etDescripcionEjercicio.text.toString()
            val repeticiones = etRepeticiones.text.toString().toIntOrNull() ?: 0
            val peso = etPeso.text.toString().toFloatOrNull() ?: 0f
            val series = etSeries.text.toString().toIntOrNull() ?: 0

            if (nombreEjercicio.isNotEmpty() && descripcionEjercicio.isNotEmpty() && repeticiones > 0 && peso > 0 && series > 0) {
                val ejercicio = Ejercicio(nombreEjercicio, descripcionEjercicio, repeticiones, peso, series)
                rEjercicios.add(ejercicio)
                // Aquí podrías actualizar un RecyclerView para mostrar los ejercicios agregados, si es necesario
            } else {
                Toast.makeText(context, "Por favor, complete todos los campos del ejercicio", Toast.LENGTH_SHORT).show()
            }
        }
        dialogBuilder.setNegativeButton("Cancelar") { _, _ -> }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}