package com.leothan.shoppingcenter.model

data class RespuestaSimple(
    val message: String,
    val success: Boolean,
    val error: String
)