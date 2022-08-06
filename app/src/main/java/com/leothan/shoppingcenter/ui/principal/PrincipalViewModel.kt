package com.leothan.shoppingcenter.ui.principal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PrincipalViewModel : ViewModel() {
    val prueba = MutableLiveData<String>().apply {
        value = "Principal"
    }

}