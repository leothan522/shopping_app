package com.leothan.shoppingcenter.ui.principal

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.leothan.shoppingcenter.apis.Direcciones
import com.leothan.shoppingcenter.apis.WebViewHelper
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

        val loading = binding.layoutWeb.layoutLoading.loading
        val swipe = binding.layoutWeb.swipe
        val webView = binding.layoutWeb.webView
        loading.isVisible = true
        WebViewHelper.webView(webView, Direcciones().URL_PRINCIPAL, activity, loading)
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