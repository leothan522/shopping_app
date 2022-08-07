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
import com.leothan.shoppingcenter.databinding.ActivityRecuperarBinding
import com.leothan.shoppingcenter.model.RespuestaSimple
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecuperarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecuperarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecuperarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recuperarClave(
            binding.layoutRecuperar.btnEnviar,
            binding.layoutRecuperar.etEmail
        )

    }

    private fun recuperarClave(btnEnviar: Button, etEmail: EditText) {
        btnEnviar.setOnClickListener {
            verLoading(true)
            if(validarCampos(etEmail)){
                getRecuperar(etEmail.text.toString())
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
            binding.layoutRecuperar.tiEmail.visibility = View.INVISIBLE
            binding.layoutRecuperar.tvText.visibility = View.INVISIBLE
            binding.layoutRecuperar.btnEnviar.visibility = View.INVISIBLE

        }else{
            binding.layoutLoading.loading.isVisible = false
            binding.layoutRecuperar.tiEmail.visibility = View.VISIBLE
            binding.layoutRecuperar.tvText.visibility = View.VISIBLE
            binding.layoutRecuperar.btnEnviar.visibility = View.VISIBLE
        }
    }

    private fun getRecuperar(email: String) {
        /*val retrofitBuilder = Retrofit.Builder()
            .baseUrl(Direcciones().BASE_URL_ANDROID)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitInterface::class.java)*/
        val retrofitData = RetrofitHelper.getAndroid().recuperarCLave(email)
        retrofitData.enqueue(object : Callback<RespuestaSimple?> {
            override fun onResponse(
                call: Call<RespuestaSimple?>,
                response: Response<RespuestaSimple?>
            ) {
                val resultado = response.body()
                if (resultado?.success == true){
                    //se envio correo
                    Dialogs().emailSend(
                        binding.layoutRecuperar.btnEnviar.context,
                        layoutInflater.inflate(R.layout.dialog_email_send, null),
                        this@RecuperarActivity
                    )
                }else{
                    Dialogs().showDialog(
                        binding.layoutRecuperar.btnEnviar.context,
                        layoutInflater.inflate(R.layout.dialog_mensaje_error, null),
                        resultado?.message.toString(),
                        "Email no encontrado"
                    )
                    verLoading(false)
                    //Toast.makeText(binding.layoutRecuperar.btnEnviar.context, resultado?.message, Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<RespuestaSimple?>, t: Throwable) {
                Toast.makeText(binding.layoutRecuperar.btnEnviar.context, "Error " + t.toString(), Toast.LENGTH_SHORT).show()
                Log.d("RecuperarActivity", t.toString())
                verLoading(false)
                Dialogs().noInternet(
                    binding.layoutRecuperar.btnEnviar.context,
                    layoutInflater.inflate(R.layout.dialog_no_internet, null)
                )
            }
        })
    }

    private fun validarCampos(etEmail: EditText): Boolean {
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
        return resultado
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}