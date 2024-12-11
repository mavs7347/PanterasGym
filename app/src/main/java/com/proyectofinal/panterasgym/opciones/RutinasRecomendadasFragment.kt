package com.proyectofinal.panterasgym.opciones

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.proyectofinal.panterasgym.R
import com.proyectofinal.panterasgym.clases.Ejercicio
import com.proyectofinal.panterasgym.clases.RutinaInfo
import com.proyectofinal.panterasgym.databinding.FragmentRutinasRecomendadasBinding

class RutinasRecomendadasFragment : Fragment() {
    private var _binding: FragmentRutinasRecomendadasBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = RutinasRecomendadasFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRutinasRecomendadasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarRutinas()
        configurarListeners()
    }

    private fun configurarRutinas() {
        // Configurar información de las rutinas dinámicamente
        configurarRutina(
            binding.txtTituloCardio,
            binding.txtDescripcionCardio,
            binding.txtCreacionCardio,
            binding.txtDuracionCardio,
            R.string.rutina_cardio,
            R.string.descripcion_cardio,
            R.string.creacion_predeterminada,
            R.string.duracion_cardio
        )

        configurarRutina(
            binding.txtTituloAdbomen,
            binding.txtDescripcionAbdomen,
            binding.txtCreacionAbdomen,
            binding.txtDuracionAbdomen,
            R.string.rutina_abdomen,
            R.string.descripcion_abdomen,
            R.string.creacion_predeterminada,
            R.string.duracion_abdomen
        )

        configurarRutina(
            binding.txtTituloBicep,
            binding.txtDescripcionBicep,
            binding.txtCreacionBicep,
            binding.txtDuracionBicep,
            R.string.rutina_bicep,
            R.string.descripcion_bicep,
            R.string.creacion_predeterminada,
            R.string.duracion_bicep
        )
    }

    private fun configurarRutina(
        tituloView: TextView,
        descripcionView: TextView,
        creacionView: TextView,
        duracionView: TextView,
        tituloRes: Int,
        descripcionRes: Int,
        creacionRes: Int,
        duracionRes: Int
    ) {
        tituloView.text = getString(tituloRes)
        descripcionView.text = getString(descripcionRes)
        creacionView.text = getString(creacionRes)
        duracionView.text = getString(duracionRes)
    }

    private fun configurarListeners() {
        binding.imgRutinaCardio.setOnClickListener {
            val ejercicios = listOf(
                Ejercicio(
                    eNombre = getString(R.string.ejercicio_bicicleta),
                    eDescripcion = getString(R.string.descripcion_bicicleta),
                    eRepeticiones = 15,
                    ePeso = 0f,
                    eSeries = 1
                ),
                Ejercicio(
                    eNombre = getString(R.string.ejercicio_jumping_jack),
                    eDescripcion = getString(R.string.descripcion_jumping_jack),
                    eRepeticiones = 12,
                    ePeso = 0f,
                    eSeries = 4
                ),
                Ejercicio(
                    eNombre = getString(R.string.ejercicio_saltar_cuerda),
                    eDescripcion = getString(R.string.descripcion_abdominal_crunch),
                    eRepeticiones = 12,
                    ePeso = 0f,
                    eSeries = 4
                ),
                Ejercicio(
                    eNombre = getString(R.string.ejercicio_maquina_correr),
                    eDescripcion = getString(R.string.descripcion_maquina_correr),
                    eRepeticiones = 12,
                    ePeso = 0f,
                    eSeries = 4
                )
            )
            abrirRutinaInfo(ejercicios)
        }

        binding.imgRutinaAbdomen.setOnClickListener {
            val ejercicios = listOf(
                Ejercicio(
                    eNombre = getString(R.string.ejercicio_abdominal_crunch),
                    eDescripcion = getString(R.string.descripcion_abdominal_crunch),
                    eRepeticiones = 12,
                    ePeso = 0f,
                    eSeries = 4
                ),
                Ejercicio(
                    eNombre = getString(R.string.ejercicio_extension_tronco),
                    eDescripcion = getString(R.string.descripcion_extension_tronco),
                    eRepeticiones = 12,
                    ePeso = 0f,
                    eSeries = 4
                )
            )
            abrirRutinaInfo(ejercicios)
        }

        binding.imgRutinaBicep.setOnClickListener {
            val ejercicios = listOf(
                Ejercicio(
                    eNombre = getString(R.string.ejercicio_curl_biceps),
                    eDescripcion = getString(R.string.descripcion_curl_biceps),
                    eRepeticiones = 12,
                    ePeso = 0f,
                    eSeries = 4
                ),
                Ejercicio(
                    eNombre = getString(R.string.ejercicio_flexiones),
                    eDescripcion = getString(R.string.descripcion_flexiones),
                    eRepeticiones = 12,
                    ePeso = 0f,
                    eSeries = 4
                )
            )
            abrirRutinaInfo(ejercicios)
        }
    }

    private fun abrirRutinaInfo(ejercicios: List<Ejercicio>) {
        val intent = Intent(activity, RutinaInfo::class.java).apply {
            putExtra("ejercicios", ArrayList(ejercicios))
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
