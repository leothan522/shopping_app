package com.leothan.shoppingcenter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.leothan.shoppingcenter.databinding.ActivityMain2Binding
import com.leothan.shoppingcenter.prefs.ShoppingCenterApplication.Companion.prefs

class Main2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvName.text = prefs.getName()
        binding.tvEmail.text = prefs.getEmail()
        binding.tvtelefono.text= prefs.getTelefono()

        cerrar()
    }

    fun cerrar(){
        binding.btnCerrar.setOnClickListener {
            prefs.wipe()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}