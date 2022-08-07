package com.leothan.shoppingcenter.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leothan.shoppingcenter.prefs.ShoppingCenterApplication.Companion.prefs
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val name = MutableLiveData<String>().apply {
        value = prefs.getName()
    }
    val email = MutableLiveData<String>().apply {
        value = prefs.getEmail()
    }
    val telefono = MutableLiveData<String>().apply {
        value = prefs.getTelefono()
    }

}