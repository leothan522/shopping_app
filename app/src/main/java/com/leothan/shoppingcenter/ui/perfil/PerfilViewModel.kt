package com.leothan.shoppingcenter.ui.perfil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leothan.shoppingcenter.prefs.ShoppingCenterApplication

class PerfilViewModel : ViewModel() {
    val name = MutableLiveData<String>().apply {
        value = ShoppingCenterApplication.prefs.getName()
    }
    val email = MutableLiveData<String>().apply {
        value = ShoppingCenterApplication.prefs.getEmail()
    }
    val telefono = MutableLiveData<String>().apply {
        value = ShoppingCenterApplication.prefs.getTelefono()
    }
}