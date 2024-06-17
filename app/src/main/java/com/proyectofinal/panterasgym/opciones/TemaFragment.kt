package com.proyectofinal.panterasgym.opciones

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import com.proyectofinal.panterasgym.R

class TemaFragment : Fragment() {
    private lateinit var vista: View

    private lateinit var temaGroup: RadioGroup
    private lateinit var light: RadioButton
    private lateinit var dark: RadioButton
    private lateinit var default: RadioButton

    companion object {
        fun newInstance() = TemaFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences: SharedPreferences? = activity?.getSharedPreferences("app_preferences", MODE_PRIVATE)
        if (preferences?.contains("theme")!!) {
            if (preferences.getInt("theme", 0) == 1) {
                light.setChecked(true)
            } else if (preferences.getInt("theme", 0) == 2) {
                dark.setChecked(true)
            } else if (preferences.getInt("theme", 0) == 3) {
                default.setChecked(true)
            }
        } else {
            default.setChecked(true)
        }
        val editor: SharedPreferences.Editor? = preferences?.edit()
        light.setOnClickListener{
            editor?.putBoolean("isChangingTheme", true)
            editor?.putInt("theme", 1)
            editor?.apply()
            activity?.recreate()
        }
        dark.setOnClickListener{
            editor?.putBoolean("isChangingTheme", true)
            editor?.putInt("theme", 2)
            editor?.apply()
            activity?.recreate()
        }
        default.setOnClickListener{
            editor?.putBoolean("isChangingTheme", true)
            editor?.putInt("theme", 3)
            editor?.apply()
            activity?.recreate()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vista = inflater.inflate(R.layout.fragment_tema, container, false)

        temaGroup = vista.findViewById(R.id.rbtnGroupThemes)
        light = vista.findViewById(R.id.rbtnLight)
        dark = vista.findViewById(R.id.rbtnDark)
        default = vista.findViewById(R.id.rbtnDefault)

        return vista
    }
}