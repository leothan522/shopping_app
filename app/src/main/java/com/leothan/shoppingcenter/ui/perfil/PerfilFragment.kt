package com.leothan.shoppingcenter.ui.perfil

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leothan.shoppingcenter.databinding.FragmentPerfilBinding
import com.leothan.shoppingcenter.prefs.ShoppingCenterApplication.Companion.prefs

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val perfilViewModel = ViewModelProvider(this).get(PerfilViewModel::class.java)
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val title = binding.layoutPerfil.tvTitle
        val nombre = binding.layoutPerfil.tvNombre
        val email = binding.layoutPerfil.tvEMail
        val telefono = binding.layoutPerfil.tvtelefono

        title.text = prefs.getName()
        nombre.text = prefs.getName()
        email.text = prefs.getEmail()
        telefono.text = prefs.getTelefono()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}