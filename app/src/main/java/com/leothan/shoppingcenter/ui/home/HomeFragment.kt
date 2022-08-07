package com.leothan.shoppingcenter.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.leothan.shoppingcenter.LoginActivity
import com.leothan.shoppingcenter.R
import com.leothan.shoppingcenter.databinding.FragmentHomeBinding
import com.leothan.shoppingcenter.prefs.ShoppingCenterApplication.Companion.prefs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val nombre = binding.layoutHome.tvUser
        val btnComprar = binding.layoutHome.btnComprar

        nombre.text = "Hola ${prefs.getName()}"

        btnComprar.setOnClickListener {
            irDestino(R.id.nav_principal)
        }

        return root
    }

    private fun irDestino(destino: Int) {
        val navController = findNavController(requireActivity(), R.id.nav_host_fragment_content_main)
        navController.navigate(destino)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}