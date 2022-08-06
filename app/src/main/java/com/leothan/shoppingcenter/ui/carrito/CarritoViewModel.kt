package com.leothan.shoppingcenter.ui.carrito

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CarritoViewModel : ViewModel() {
    val prueba = MutableLiveData<String>().apply {
        value = "Carrito"
    }
}