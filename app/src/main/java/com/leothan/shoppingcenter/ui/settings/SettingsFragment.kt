package com.leothan.shoppingcenter.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import com.leothan.shoppingcenter.R
import com.leothan.shoppingcenter.apis.RetrofitHelper
import com.leothan.shoppingcenter.databinding.FragmentSettingsBinding
import com.leothan.shoppingcenter.dialogs.Dialogs
import com.leothan.shoppingcenter.model.Usuario
import com.leothan.shoppingcenter.prefs.ShoppingCenterApplication.Companion.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root = binding.root

        showUser(settingsViewModel)

        val etName = binding.layoutAjustes.etName
        val etEmail = binding.layoutAjustes.etEmail
        val etTelefono = binding.layoutAjustes.etTelefono
        val etActual = binding.layoutAjustes.etActual
        val etPassword = binding.layoutAjustes.etPassword
        val tvID = binding.layoutAjustes.tvID
        val btnGuardar = binding.layoutAjustes.btnGuardar


        btnGuardar.setOnClickListener {
            verLoading(true)
            if (validarCampos(etName, etEmail, etTelefono, etActual, etPassword)){
                getUpdate(etName, etEmail, etTelefono, etActual, etPassword, tvID)
            }else{
                verLoading(false)
            }
        }


        return root
    }

    private fun getUpdate(
        etName: EditText,
        etEmail: EditText,
        etTelefono: EditText,
        etActual: EditText,
        etPassword: EditText,
        tvId: TextView
    ) {
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val telefono = etTelefono.text.toString()
        val actual = etActual.text.toString()
        val password = etPassword.text.toString()
        val id = tvId.text.toString()

        val retrofitData = RetrofitHelper.getAndroid().actualizarUsuario(
            name,
            email,
            telefono,
            password,
            actual,
            id
        )
        retrofitData.enqueue(object : Callback<Usuario?> {
            override fun onResponse(call: Call<Usuario?>, response: Response<Usuario?>) {
                val resultado = response.body()
                if (resultado?.success == true){

                    val navView: NavigationView? = activity?.findViewById(R.id.nav_view)
                    val header: View? = navView?.getHeaderView(0)
                    val headerNombre: TextView? = header?.findViewById(R.id.tvNameMain)
                    val headerCorreo: TextView? = header?.findViewById(R.id.tvEmailMain)

                    if (resultado.name != "false"){
                        prefs.saveName(resultado.name)
                        binding.layoutAjustes.tvNombre.text = prefs.getName()
                        headerNombre?.text = prefs.getName()
                    }

                    if (resultado.email != "false"){
                        prefs.saveEmail(resultado.email)
                        binding.layoutAjustes.tvEMail.text = prefs.getEmail()
                        headerCorreo?.text = prefs.getEmail()
                    }

                    if (resultado.telefono != "false"){
                        prefs.saveTelefono(resultado.telefono)
                        binding.layoutAjustes.tvTelefono.text = prefs.getTelefono()
                    }

                    Dialogs().showDialog(
                        etEmail.context,
                        layoutInflater.inflate(R.layout.dialog_mensaje_success, null),
                        resultado?.message.toString(),
                        "¡Éxito!"
                    )

                    //Toast.makeText(etEmail.context, resultado.message, Toast.LENGTH_LONG).show()

                }else{
                    var titulo: String
                    if (resultado?.error.toString() == "false"){
                        titulo = ""
                    }else{
                        titulo = resultado?.error.toString()
                    }
                    Dialogs().showDialog(
                        etEmail.context,
                        layoutInflater.inflate(R.layout.dialog_mensaje_error, null),
                        resultado?.message.toString(),
                        titulo
                    )
                }
                limpiarCampos(etName, etEmail, etTelefono, etActual, etPassword)
                verLoading(false)
            }

            override fun onFailure(call: Call<Usuario?>, t: Throwable) {
                Toast.makeText(etEmail.context, "Error " + t.toString(), Toast.LENGTH_SHORT).show()
                Log.d("SettingsFragment", t.toString())
                verLoading(false)
                Dialogs().noInternet(
                    etEmail.context,
                    layoutInflater.inflate(R.layout.dialog_no_internet, null)
                )
            }
        })



    }

    private fun limpiarCampos(
        etName: EditText,
        etEmail: EditText,
        etTelefono: EditText,
        etActual: EditText,
        etPassword: EditText
    ) {
        etName.text = null
        etEmail.text = null
        etTelefono.text = null
        etActual.text = null
        etPassword.text = null
    }

    private fun validarCampos(
        etName: EditText,
        etEmail: EditText,
        etTelefono: EditText,
        etActual: EditText,
        etPassword: EditText
    ): Boolean {

        var resultado = true

        if (!etName.text.isNullOrEmpty()){
            if (etName.text.length < 4){
                etName.error = getText(R.string.min_4_caracteres)
                resultado = false
            }
        }

        if (!etEmail.text.isNullOrEmpty()){
            val patterns = Patterns.EMAIL_ADDRESS
            if (!patterns.matcher(etEmail.text.toString()).matches()){
                etEmail.error = getText(R.string.error_email)
                resultado = false
            }
        }

        if (!etTelefono.text.isNullOrEmpty()){
            val patterns = Patterns.PHONE
            if (!patterns.matcher(etTelefono.text.toString()).matches()){
                etTelefono.error = getText(R.string.error_telefono)
                resultado = false
            }
        }

        if (!etActual.text.isNullOrEmpty()){
            if (etActual.text.length < 8){
                etActual.error = getText(R.string.min_8_caracteres)
                resultado = false
            }
            if (etPassword.text.isNullOrEmpty()){
                etPassword.error = getText(R.string.ingrese_password)
                resultado = false
            }
        }

        if (!etPassword.text.isNullOrEmpty()){
            if (etPassword.text.length < 8){
                etPassword.error = getText(R.string.min_8_caracteres)
                resultado = false
            }
            if (etActual.text.isNullOrEmpty()){
                etActual.error = getText(R.string.ingrese_password)
                resultado = false
            }
        }

        if (etEmail.text.isNullOrEmpty()
            && etName.text.isNullOrEmpty()
            && etTelefono.text.isNullOrEmpty()
            && etActual.text.isNullOrEmpty()
            && etPassword.text.isNullOrEmpty()){
            Toast.makeText(etEmail.context, "No se realizaron cambios.", Toast.LENGTH_LONG).show()
            resultado = false
        }

        return resultado
    }

    private fun verLoading(loading: Boolean) {
        val _loading = binding.layoutAjustes.layoutLoading.loading
        val tlName = binding.layoutAjustes.tlName
        val tlEmail = binding.layoutAjustes.tlEmail
        val tlTelefono = binding.layoutAjustes.tlTelefono
        val tlActual = binding.layoutAjustes.tlActual
        val tlPassword = binding.layoutAjustes.tlPassword
        val btnGuardar = binding.layoutAjustes.btnGuardar

        _loading.isVisible = loading

        if (loading){
            tlName.visibility = View.INVISIBLE
            tlEmail.visibility = View.INVISIBLE
            tlTelefono.visibility = View.INVISIBLE
            tlActual.visibility = View.INVISIBLE
            tlPassword.visibility = View.INVISIBLE
            btnGuardar.visibility = View.INVISIBLE
        }else{
            tlName.visibility = View.VISIBLE
            tlEmail.visibility = View.VISIBLE
            tlTelefono.visibility = View.VISIBLE
            tlActual.visibility = View.VISIBLE
            tlPassword.visibility = View.VISIBLE
            btnGuardar.visibility = View.VISIBLE
        }
    }

    private fun showUser(settingsViewModel: SettingsViewModel) {
        val nombre = binding.layoutAjustes.tvNombre
        val email = binding.layoutAjustes.tvEMail
        val telefono = binding.layoutAjustes.tvTelefono
        val id = binding.layoutAjustes.tvID
        nombre.text = prefs.getName()
        email.text = prefs.getEmail()
        telefono.text = prefs.getTelefono()
        id.text = prefs.getID().toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}