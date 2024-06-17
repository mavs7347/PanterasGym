package com.proyectofinal.panterasgym.opciones

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.proyectofinal.panterasgym.ProgresoViewModel
import com.proyectofinal.panterasgym.R
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class ProgresoFragment : Fragment() {
    private lateinit var progresoViewModel: ProgresoViewModel
    private lateinit var calendarView: CalendarView
    private var selectedDate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progresoViewModel = ViewModelProvider(requireActivity()).get(ProgresoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_progreso, container, false)

        // Inicializar vistas
        calendarView = view.findViewById(R.id.calendarView)

        // Listener para el CalendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
            buscarFecha()
        }

        return view
    }

    private fun buscarFecha() {
        val fechaSeleccionada = obtenerFechaSeleccionada()

        // Observar cambios en los datos de progreso
        progresoViewModel.progreso.observe(viewLifecycleOwner, Observer { progreso ->
            // Verificar si progreso no es nulo y manejar las fechas adecuadamente
            if (progreso != null) {
                val listaFechas = progreso.fecha // Suponiendo que 'fechas' es una lista de fechas en tu ProgresoViewModel

                // Lista para almacenar índices de coincidencias
                val indicesCoincidentes = mutableListOf<Int>()

                // Buscar todas las ocurrencias de la fecha seleccionada
                for ((index, fecha) in listaFechas.withIndex()) {
                    if (fecha == fechaSeleccionada) {
                        indicesCoincidentes.add(index)
                    }
                }

                // Mostrar resultados
                if (indicesCoincidentes.isNotEmpty()) {
                    // Mostrar un mensaje o hacer alguna acción con las fechas encontradas
                    val mensaje = "${progreso.cNombre} acabaste con ${indicesCoincidentes.size} RUTINAS en la fecha seleccionada."
                    Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()

                    // Ejemplo de acceso a todas las fechas coincidentes
                    for (indice in indicesCoincidentes) {
                        val fechaCoincidente = listaFechas[indice]
                        // Aquí puedes hacer algo con cada fecha coincidente, por ejemplo:
                        println("Fecha coincidente: $fechaCoincidente")
                    }
                } else {
                    // Manejar el caso cuando no se encuentra ninguna ocurrencia de la fecha seleccionada
                    Toast.makeText(requireContext(), "No se encontraron ocurrencias de la fecha seleccionada.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Manejar el caso cuando 'progreso' es nulo
                Toast.makeText(requireContext(), "No se encontraron datos de progreso.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun obtenerFechaSeleccionada(): String {
        val formato = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return formato.format(selectedDate)
    }
}
