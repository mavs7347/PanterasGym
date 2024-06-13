package com.proyectofinal.panterasgym.opciones

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.proyectofinal.panterasgym.R

class PantallaPrincipalFragment : Fragment() {

    companion object {
        fun newInstance() = PantallaPrincipalFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_pantalla_principal, container, false)
    }
}