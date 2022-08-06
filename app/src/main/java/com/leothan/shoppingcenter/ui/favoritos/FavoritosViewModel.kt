package com.leothan.shoppingcenter.ui.favoritos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoritosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Favoritos Fragment"
    }
    val text: LiveData<String> = _text
}