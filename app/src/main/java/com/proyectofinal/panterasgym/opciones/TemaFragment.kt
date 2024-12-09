package com.proyectofinal.panterasgym.opciones

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.proyectofinal.panterasgym.R

class TemaFragment : Fragment() {

    private lateinit var vista: View
    private lateinit var temaGroup: RadioGroup
    private lateinit var light: RadioButton
    private lateinit var dark: RadioButton
    private lateinit var default: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_tema, container, false)

        temaGroup = vista.findViewById(R.id.rbtnGroupThemes)
        light = vista.findViewById(R.id.rbtnLight)
        dark = vista.findViewById(R.id.rbtnDark)
        default = vista.findViewById(R.id.rbtnDefault)

        configurarBotones()
        return vista
    }

    private fun configurarBotones() {
        val preferences = activity?.getSharedPreferences("app_preferences", android.content.Context.MODE_PRIVATE)
        val editor = preferences?.edit()

        val theme = preferences?.getInt("theme", 3)
        when (theme) {
            1 -> light.isChecked = true
            2 -> dark.isChecked = true
            3 -> default.isChecked = true
        }

        temaGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbtnLight -> {
                    Log.d("TemaFragment", "Seleccionado tema Light")
                    editor?.putInt("theme", 1)
                }
                R.id.rbtnDark -> {
                    Log.d("TemaFragment", "Seleccionado tema Dark")
                    editor?.putInt("theme", 2)
                }
                R.id.rbtnDefault -> {
                    Log.d("TemaFragment", "Seleccionado tema Default")
                    editor?.putInt("theme", 3)
                }
            }
            editor?.apply()
            activity?.recreate() // Reinicia actividad para aplicar el tema
        }
    }
}
