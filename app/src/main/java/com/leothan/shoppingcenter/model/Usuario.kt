package com.leothan.shoppingcenter.model

data class Usuario(
    val email: String,
    val id: String,
    val message: String,
    val name: String,
    val success: Boolean,
    val telefono: String,
    val error: String
)