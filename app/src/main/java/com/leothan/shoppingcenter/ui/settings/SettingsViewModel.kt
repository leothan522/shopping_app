package com.leothan.shoppingcenter.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    val prueba = MutableLiveData<String>().apply {
        value = "Ajustes"
    }
}