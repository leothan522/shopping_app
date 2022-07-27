package com.leothan.shoppingcenter.apis

import com.leothan.shoppingcenter.dataclass.RespuestaSimple
import com.leothan.shoppingcenter.dataclass.Usuario
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiRetrofitInterface {

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