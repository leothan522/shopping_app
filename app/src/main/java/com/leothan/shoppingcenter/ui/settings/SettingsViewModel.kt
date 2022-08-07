package com.leothan.shoppingcenter.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leothan.shoppingcenter.prefs.ShoppingCenterApplication.Companion.prefs

class SettingsViewModel : ViewModel() {
    val name = MutableLiveData<String>().apply {
        value = prefs.getName()
    }
    val email = MutableLiveData<String>().apply {
        value = prefs.getEmail()
    }
    val telefono = MutableLiveData<String>().apply {
        value = prefs.getTelefono()
    }
    val id = MutableLiveData<Int>().apply {
        value = prefs.getID()
    }
}