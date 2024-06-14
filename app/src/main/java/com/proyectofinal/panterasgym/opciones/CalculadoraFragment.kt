package com.proyectofinal.panterasgym.opciones

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.proyectofinal.panterasgym.ClienteViewModel
import kotlin.math.round
import com.proyectofinal.panterasgym.R
import com.proyectofinal.panterasgym.clases.Cliente
import com.proyectofinal.panterasgym.clases.Rutina
import kotlin.math.pow

class CalculadoraFragment : Fragment() {
    private lateinit var clienteViewModel: ClienteViewModel

    private val objCliente: Cliente = Cliente()

    private lateinit var vista: View

    private lateinit var peso: EditText
    private lateinit var altura: EditText
    private lateinit var indiceMasaCorporal: TextView
    private lateinit var salud: TextView
    private lateinit var progresoIndiceMasaCorporal: ProgressBar

    companion object {
        fun newInstance() = CalculadoraFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clienteViewModel = ViewModelProvider(requireActivity()).get(ClienteViewModel::class.java)

        peso.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (peso.text.isNotBlank()) {
                    try {
                        objCliente.cPeso = peso.text.toString().toFloat()
                        actualizarDatos()
                        actualizarCliente()
                    } catch (e: NumberFormatException) {

                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
        altura.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (altura.text.isNotBlank()) {
                    try {
                        objCliente.cAltura = altura.text.toString().toFloat()
                        actualizarDatos()
                        actualizarCliente()
                    } catch (e: NumberFormatException) {

                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        objCliente.cNombre = arguments?.getString("cNombre")!!
        objCliente.cCorreo = arguments?.getString("cCorreo")!!
        objCliente.cContrasena = arguments?.getString("cContrasena")!!
        objCliente.cEdad = arguments?.getInt("cEdad")!!
        objCliente.cPeso = arguments?.getFloat("cPeso")!!
        objCliente.cAltura = arguments?.getFloat("cAltura")!!
        objCliente.cRecordar = arguments?.getBoolean("cRecordar")!!
        objCliente.cRutinas = arguments?.getSerializable("cRutinas") as ArrayList<Rutina>

        vista = inflater.inflate(R.layout.fragment_calculadora, container, false)
        peso = vista.findViewById(R.id.edtPeso)
        altura = vista.findViewById(R.id.edtAltura)
        indiceMasaCorporal = vista.findViewById(R.id.lblIndiceMasaCorporal)
        salud = vista.findViewById(R.id.lblSalud)
        progresoIndiceMasaCorporal = vista.findViewById(R.id.pgrIndiceMasaCorporal)

        peso.setText(objCliente.cPeso.toString())
        altura.setText(objCliente.cAltura.toString())
        actualizarDatos()
        return vista
    }

    private fun actualizarDatos() {
        indiceMasaCorporal.setText((round(objCliente.cIndiceMasaCorporal * (10.0f.pow(2))) / (10.0f.pow(2))).toString())
        progresoIndiceMasaCorporal.progress = round(objCliente.cIndiceMasaCorporal).toInt()
        if (objCliente.cIndiceMasaCorporal <= 18.5) {
            salud.setText("Peso insuficiente")
        } else if (objCliente.cIndiceMasaCorporal >= 18.5 && objCliente.cIndiceMasaCorporal <= 24.9) {
            salud.setText("Peso saludable")
        } else if (objCliente.cIndiceMasaCorporal >= 25.0 && objCliente.cIndiceMasaCorporal <= 29.9) {
            salud.setText("Sobrepeso")
        } else if (objCliente.cIndiceMasaCorporal >= 30.0) {
            salud.setText("Obesidad")
        }
    }

    private fun actualizarCliente() {
        clienteViewModel.cliente.value = objCliente
    }
}
/*
val editText = findViewById<EditText>(R.id.tu_edittext_id)
editText.addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        // Código a ejecutar después de que el texto ha cambiado
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Código a ejecutar antes de que el texto cambie
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Código a ejecutar cuando el texto está cambiando
    }
})

 */