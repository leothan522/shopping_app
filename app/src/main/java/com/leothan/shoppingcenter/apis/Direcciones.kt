package com.leothan.shoppingcenter.apis

import com.leothan.shoppingcenter.prefs.ShoppingCenterApplication.Companion.prefs

class Direcciones() {

    private val id: Int = prefs.getID()

    val BASE_URL_LOCAL: String = "http://192.168.10.108:8080/shopping/android/"
    val BASE_URL_ANDROID: String = "http://caracashoppingcenter.com/android/"
    val BASE_URL_LARAVEL: String = "http://caracashoppingcenter.com/laravel/public/android/"

    val URL_PRINCIPAL: String = "$BASE_URL_LARAVEL$id/home"
    val URL_CATEGORIAS: String = "$BASE_URL_LARAVEL$id/listarcategorias"
    val URL_TIENDAS: String = "$BASE_URL_LARAVEL$id/listartiendas"
    val URL_FAVORITOS: String = "$BASE_URL_LARAVEL$id/favoritos"
    val URL_CARRITO: String = "$BASE_URL_LARAVEL$id/carrito"
    val URL_PEDIDOS: String = "$BASE_URL_LARAVEL$id/verpedidos"

}