package com.leothan.shoppingcenter.ui.carrito

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leothan.shoppingcenter.databinding.FragmentCarritoBinding

class CarritoFragment : Fragment() {

    private var _binding: FragmentCarritoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val carritoViewModel = ViewModelProvider(this).get(CarritoViewModel::class.java)
        _binding = FragmentCarritoBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val texto = binding.tvPruebaHola
        carritoViewModel.prueba.observe(viewLifecycleOwner){
            texto.text = it
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}