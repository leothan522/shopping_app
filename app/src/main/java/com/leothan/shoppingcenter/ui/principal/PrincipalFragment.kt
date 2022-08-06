package com.leothan.shoppingcenter.ui.principal

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leothan.shoppingcenter.databinding.FragmentPrincipalBinding

class PrincipalFragment : Fragment() {

    private var _binding: FragmentPrincipalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val principalViewModel =
            ViewModelProvider(this).get(PrincipalViewModel::class.java)
        _binding = FragmentPrincipalBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val texto = binding.tvPruebaHola

        principalViewModel.prueba.observe(viewLifecycleOwner){
            texto.text = it
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}