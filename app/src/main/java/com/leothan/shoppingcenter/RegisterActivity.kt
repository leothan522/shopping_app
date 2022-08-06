package com.leothan.shoppingcenter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.leothan.shoppingcenter.dialogs.Dialogs
import com.leothan.shoppingcenter.apis.RetrofitHelper
import com.leothan.shoppingcenter.databinding.ActivityRegisterBinding
import com.leothan.shoppingcenter.model.Usuario
import com.leothan.shoppingcenter.prefs.ShoppingCenterApplication.Companion.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goLogin(binding.layoutRegister.goLogin)
        registrarUsuario(
            binding.layoutRegister.btnRegister,
            binding.layoutRegister.etName,
            binding.layoutRegister.etEmail,
            binding.layoutRegister.etTelefono,
            binding.layoutRegister.etPassword
        )

    }

    private fun registrarUsuario(
        btnRegister: Button,
        etNombre: EditText,
        etEmail: EditText,
        etTelefono: EditText,
        etPassword: EditText
    ) {
        btnRegister.setOnClickListener {
            verLoading(true)
            if (validarCampos(etNombre, etEmail, etPassword, etTelefono)){
                //registrar usuario
                getRegister(
                    etNombre.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString(),
                    etTelefono.text.toString()
                )
            }else{
                //campos no validados
                Toast.makeText(this, "Los campos no cumplen con la validacion", Toast.LENGTH_SHORT).show()
                verLoading(false)
            }
        }
    }

    private fun validarCampos(
        etNombre: EditText,
        etEmail: EditText,
        etPassword: EditText,
        etTelefono: EditText
    ): Boolean {
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

        if (etNombre.text.isNullOrEmpty()){
            etNombre.error = getText(R.string.ingrese_nombre)
            resultado = false
        }else{
            if (etNombre.text.length < 4){
                etNombre.error = getText(R.string.min_4_caracteres)
                resultado = false
            }
        }

        if (etTelefono.text.isNullOrEmpty()){
            etTelefono.error = getText(R.string.ingrese_telefono)
            resultado = false
        }else{
            val patterns = Patterns.PHONE
            if (!patterns.matcher(etTelefono.text.toString()).matches()){
                etTelefono.error = getText(R.string.error_telefono)
                resultado = false
            }
        }

        return resultado
    }

    private fun getRegister(
        nombre: String,
        email: String,
        password: String,
        telefono: String
    ) {
        /*val retrofitBuilder = Retrofit.Builder()
            .baseUrl(Direcciones().BASE_URL_ANDROID)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitInterface::class.java)*/
        val retrofitData = RetrofitHelper.getAndroid().registrarUsuario(
            nombre,
            email,
            telefono,
            password
        )
        retrofitData.enqueue(object : Callback<Usuario?> {
            override fun onResponse(call: Call<Usuario?>, response: Response<Usuario?>) {
                val resultado = response.body()
                if (resultado?.success == true){
                    prefs.saveLogin(true)
                    prefs.saveID(resultado.id.toInt())
                    prefs.saveEmail(resultado.email)
                    prefs.saveName(resultado.name)
                    prefs.saveTelefono(resultado?.telefono.toString())
                    val intent= Intent(binding.layoutRegister.btnRegister.context, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                }else{
                    binding.layoutRegister.etEmail.error = getText(R.string.error_email_registrado)
                    Dialogs().showDialog(
                        binding.layoutRegister.btnRegister.context,
                        layoutInflater.inflate(R.layout.dialog_mensaje_error, null),
                        resultado?.message.toString(),
                        "Email no disponible"
                    )
                    //Toast.makeText(binding.layoutRegister.btnRegister.context, resultado?.message, Toast.LENGTH_LONG).show()
                }
                verLoading(false)
            }

            override fun onFailure(call: Call<Usuario?>, t: Throwable) {
                Toast.makeText(binding.layoutRegister.btnRegister.context, "Error " + t.toString(), Toast.LENGTH_SHORT).show()
                Log.d("RegisterActivity", t.toString())
                verLoading(false)
                Dialogs().noInternet(
                    binding.layoutRegister.btnRegister.context,
                    layoutInflater.inflate(R.layout.dialog_no_internet, null)
                )
            }
        })
    }

    private fun verLoading(loading: Boolean) {
        if (loading){
            binding.layoutLoading.loading.isVisible = true
            binding.layoutRegister.etEmail.visibility = View.INVISIBLE
            binding.layoutRegister.etPassword.visibility = View.INVISIBLE
            binding.layoutRegister.etName.visibility = View.INVISIBLE
            binding.layoutRegister.etPassword.visibility = View.INVISIBLE
            binding.layoutRegister.etTelefono.visibility = View.INVISIBLE
            binding.layoutRegister.goLogin.visibility = View.INVISIBLE
            binding.layoutRegister.btnRegister.visibility = View.INVISIBLE
        }else{
            binding.layoutLoading.loading.isVisible = false
            binding.layoutRegister.etEmail.visibility = View.VISIBLE
            binding.layoutRegister.etPassword.visibility = View.VISIBLE
            binding.layoutRegister.etName.visibility = View.VISIBLE
            binding.layoutRegister.etPassword.visibility = View.VISIBLE
            binding.layoutRegister.etTelefono.visibility = View.VISIBLE
            binding.layoutRegister.goLogin.visibility = View.VISIBLE
            binding.layoutRegister.btnRegister.visibility = View.VISIBLE
        }
    }

    private fun goLogin(goLogin: Button) {
        goLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}