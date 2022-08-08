package com.leothan.shoppingcenter.ui.categorias

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.leothan.shoppingcenter.apis.Direcciones
import com.leothan.shoppingcenter.apis.WebViewHelper
import com.leothan.shoppingcenter.databinding.FragmentCategoriasBinding

class CategoriasFragment : Fragment() {

    private var _binding: FragmentCategoriasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val categoriasViewModel =
            ViewModelProvider(this).get(CategoriasViewModel::class.java)

        _binding = FragmentCategoriasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val loading = binding.layoutWeb.layoutLoading.loading
        loading.isVisible = true
        val webView = binding.layoutWeb.webView
        WebViewHelper.webView(webView, Direcciones().URL_CATEGORIAS, activity, loading)
        val swipe = binding.layoutWeb.swipe
        swipe.setOnRefreshListener {
            webView.reload()
            swipe.isRefreshing = false
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}