package com.leothan.shoppingcenter.apis

import com.leothan.shoppingcenter.model.RespuestaSimple
import com.leothan.shoppingcenter.model.Usuario
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitInterface {

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<Usuario>

    @FormUrlEncoded
    @POST("register.php")
    fun registrarUsuario(
        @Field("name") name : String,
        @Field("email") email: String,
        @Field("telefono") telefono :String,
        @Field("password") password: String
    ) : Call<Usuario>

    @FormUrlEncoded
    @POST("recuperar.php")
    fun recuperarCLave(@Field("email") email: String) : Call<RespuestaSimple>

}