package com.leothan.shoppingcenter.ui.categorias

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoriasViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Categorias Fragment"
    }
    val text: LiveData<String> = _text
}