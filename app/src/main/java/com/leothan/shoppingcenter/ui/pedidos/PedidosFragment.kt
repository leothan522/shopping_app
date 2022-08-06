package com.leothan.shoppingcenter.ui.pedidos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leothan.shoppingcenter.R
import com.leothan.shoppingcenter.databinding.FragmentPedidosBinding

class PedidosFragment : Fragment() {

    private var _binding: FragmentPedidosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pedidosViewModel = ViewModelProvider(this).get(PedidosViewModel::class.java)
        _binding = FragmentPedidosBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val texto = binding.tvPruebaHola
        pedidosViewModel.prueba.observe(viewLifecycleOwner){
            texto.text = it
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}