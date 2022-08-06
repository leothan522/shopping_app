package com.leothan.shoppingcenter.ui.tiendas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TiendasViewModel : ViewModel() {
    val prueba = MutableLiveData<String>().apply {
        value = "Tiendas"
    }
}