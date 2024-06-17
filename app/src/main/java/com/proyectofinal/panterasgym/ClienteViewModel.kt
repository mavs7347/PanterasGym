package com.proyectofinal.panterasgym

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.proyectofinal.panterasgym.clases.Cliente

class ClienteViewModel : ViewModel() {
    val cliente: MutableLiveData<Cliente> = MutableLiveData()
}