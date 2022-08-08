package com.leothan.shoppingcenter.ui.tiendas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.leothan.shoppingcenter.apis.Direcciones
import com.leothan.shoppingcenter.apis.WebViewHelper
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

        val loading = binding.layoutWeb.layoutLoading.loading
        loading.isVisible = true
        val webView = binding.layoutWeb.webView
        WebViewHelper.webView(webView, Direcciones().URL_TIENDAS, activity, loading)
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