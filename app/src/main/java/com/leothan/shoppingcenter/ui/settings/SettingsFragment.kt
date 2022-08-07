package com.leothan.shoppingcenter.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leothan.shoppingcenter.R
import com.leothan.shoppingcenter.databinding.FragmentSettingsBinding

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

        val nombre = binding.layoutAjustes.tvNombre
        val email = binding.layoutAjustes.tvEMail
        val telefono = binding.layoutAjustes.tvTelefono

        settingsViewModel.name.observe(viewLifecycleOwner){
            nombre.text = it
        }
        settingsViewModel.email.observe(viewLifecycleOwner){
            email.text = it
        }
        settingsViewModel.telefono.observe(viewLifecycleOwner){
            telefono.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}