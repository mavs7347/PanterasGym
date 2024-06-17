package com.proyectofinal.panterasgym

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.proyectofinal.panterasgym.clases.Progreso

class ProgresoViewModel : ViewModel() {
    val progreso: MutableLiveData<Progreso> = MutableLiveData()
}