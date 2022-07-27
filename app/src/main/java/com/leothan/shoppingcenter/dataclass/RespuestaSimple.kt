package com.leothan.shoppingcenter.dataclass

data class RespuestaSimple(
    val message: String,
    val success: Boolean,
    val error: String
)