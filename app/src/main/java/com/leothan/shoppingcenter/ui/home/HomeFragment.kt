package com.leothan.shoppingcenter.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.leothan.shoppingcenter.LoginActivity
import com.leothan.shoppingcenter.databinding.FragmentHomeBinding
import com.leothan.shoppingcenter.prefs.ShoppingCenterApplication.Companion.prefs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        val name = binding.tvName
        val email = binding.tvEmail
        val telefono = binding.tvtelefono
        val boton = binding.btnCerrar
        homeViewModel.name.observe(viewLifecycleOwner){
            name.text = it
        }
        homeViewModel.email.observe(viewLifecycleOwner){
            email.text = it
        }
        homeViewModel.telefono.observe(viewLifecycleOwner){
            telefono.text = it
        }

        boton.setOnClickListener {
            cerrar()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun cerrar(){
        prefs.wipe()
        activity?.let {
            it.startActivity(Intent(it, LoginActivity::class.java))
            it.finish()
        }
    }
}