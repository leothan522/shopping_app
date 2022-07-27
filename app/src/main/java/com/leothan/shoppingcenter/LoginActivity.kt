package com.leothan.shoppingcenter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.leothan.shoppingcenter.Dialogs.Dialogs
import com.leothan.shoppingcenter.apis.ApiRetrofitInterface
import com.leothan.shoppingcenter.apis.Direcciones
import com.leothan.shoppingcenter.databinding.ActivityLoginBinding
import com.leothan.shoppingcenter.dataclass.Usuario
import com.leothan.shoppingcenter.prefs.ShoppingCenterApplication.Companion.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        iniciarSesion(
            binding.layoutLogin.btnLogin,
            binding.layoutLogin.etEmail,
            binding.layoutLogin.etPassword
        )
        goRegister()
        goRecuperarClave()

    }

    private fun goRecuperarClave() {
        binding.layoutLogin.goRecuperar.setOnClickListener {
            startActivity(Intent(this, RecuperarActivity::class.java))
            finish()
        }
    }

    private fun goRegister() {
        binding.layoutLogin.goRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    fun iniciarSesion(btnLogin: Button, etEmail: EditText, etPassword: EditText){
        btnLogin.setOnClickListener {
            verLoading(true)
            if (validarCampos(etEmail, etPassword)){
                getLogin(etEmail.text.toString(), etPassword.text.toString())
            }else{
                //capos no validados
                Toast.makeText(this, "Los campos no cumplen con la validacion", Toast.LENGTH_SHORT).show()
                verLoading(false)
            }
        }
    }

    private fun verLoading(loading: Boolean) {
        if (loading){
            binding.layoutLoading.loading.isVisible = true
            binding.layoutLogin.etEmail.visibility = View.INVISIBLE
            binding.layoutLogin.etPassword.visibility = View.INVISIBLE
            binding.layoutLogin.goRecuperar.visibility = View.INVISIBLE
            binding.layoutLogin.goRegister.visibility = View.INVISIBLE
            binding.layoutLogin.btnLogin.visibility = View.INVISIBLE
        }else{
            binding.layoutLoading.loading.isVisible = false
            binding.layoutLogin.etEmail.visibility = View.VISIBLE
            binding.layoutLogin.etPassword.visibility = View.VISIBLE
            binding.layoutLogin.goRecuperar.visibility = View.VISIBLE
            binding.layoutLogin.goRegister.visibility = View.VISIBLE
            binding.layoutLogin.btnLogin.visibility = View.VISIBLE
        }
    }

    private fun validarCampos(etEmail: EditText, etPassword: EditText): Boolean {
        var resultado = true

        if (etEmail.text.isNullOrEmpty()){
            etEmail.error = getText(R.string.ingrese_email)
            resultado = false
        }else{
            val patterns = Patterns.EMAIL_ADDRESS
            if (!patterns.matcher(etEmail.text.toString()).matches()){
                etEmail.error = getText(R.string.error_email)
                resultado = false
            }
        }

        if (etPassword.text.isNullOrEmpty()){
            etPassword.error = getText(R.string.ingrese_password)
            resultado = false
        }else{
            if (etPassword.text.length < 8){
                etPassword.error = getText(R.string.min_8_caracteres)
                resultado = false
            }
        }

        return resultado
    }


    private fun getLogin(email: String, password: String) {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(Direcciones().BASE_URL_ANDROID)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRetrofitInterface::class.java)
        val retrofitData = retrofitBuilder.login(email, password)
        retrofitData.enqueue(object : Callback<Usuario?> {
            override fun onResponse(call: Call<Usuario?>, response: Response<Usuario?>) {
                val resultado = response.body()
                if (resultado?.success == true){

                    prefs.saveLogin(true)
                    prefs.saveID(resultado.id.toInt())
                    prefs.saveEmail(resultado.email)
                    prefs.saveName(resultado.name)
                    prefs.saveTelefono(resultado?.telefono.toString())
                    startActivity(Intent(binding.layoutLogin.btnLogin.context, Main2Activity::class.java))
                    finish()

                }else{
                    Dialogs().showDialog(
                        binding.layoutLogin.btnLogin.context,
                        layoutInflater.inflate(R.layout.dialog_mensaje_error, null),
                        resultado?.message.toString(),
                        resultado?.error.toString()
                    )
                    //Toast.makeText(binding.layoutLogin.btnLogin.context, resultado?.message, Toast.LENGTH_LONG).show()
                }
                verLoading(false)
            }

            override fun onFailure(call: Call<Usuario?>, t: Throwable) {
                Toast.makeText(binding.layoutLogin.btnLogin.context, "Error " + t.toString(), Toast.LENGTH_SHORT).show()
                Log.d("LoginActivity", t.toString())
                verLoading(false)
                Dialogs().noInternet(
                    binding.layoutLogin.btnLogin.context,
                    layoutInflater.inflate(R.layout.dialog_no_internet, null)
                )
            }
        })
    }

}