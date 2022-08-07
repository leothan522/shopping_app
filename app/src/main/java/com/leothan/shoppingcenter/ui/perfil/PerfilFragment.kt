package com.leothan.shoppingcenter.ui.perfil

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leothan.shoppingcenter.databinding.FragmentPerfilBinding

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

        perfilViewModel.name.observe(viewLifecycleOwner){
            title.text = it
            nombre.text = it
        }

        perfilViewModel.email.observe(viewLifecycleOwner){
            email.text = it
        }

        perfilViewModel.telefono.observe(viewLifecycleOwner){
            telefono.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}