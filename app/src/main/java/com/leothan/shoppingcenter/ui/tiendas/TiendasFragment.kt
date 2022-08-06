package com.leothan.shoppingcenter.ui.tiendas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leothan.shoppingcenter.databinding.FragmentTiendasBinding

class TiendasFragment : Fragment() {

    private var _binding: FragmentTiendasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val tiendasViewModel = ViewModelProvider(this).get(TiendasViewModel::class.java)
        _binding = FragmentTiendasBinding.inflate(inflater, container, false)
        val root = binding.root


        val texto = binding.tvPruebaHola
        tiendasViewModel.prueba.observe(viewLifecycleOwner){
            texto.text = it
        }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}