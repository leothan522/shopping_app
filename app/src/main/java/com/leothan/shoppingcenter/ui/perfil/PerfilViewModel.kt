package com.leothan.shoppingcenter.ui.perfil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PerfilViewModel : ViewModel() {
    val prueba = MutableLiveData<String>().apply {
        value = "Perfil"
    }
}