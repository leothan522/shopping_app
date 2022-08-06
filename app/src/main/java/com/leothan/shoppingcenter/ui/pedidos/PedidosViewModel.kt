package com.leothan.shoppingcenter.ui.pedidos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PedidosViewModel : ViewModel() {
    val prueba = MutableLiveData<String>().apply {
        value = "Pedidos"
    }
}