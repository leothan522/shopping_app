package com.leothan.shoppingcenter.ui.favoritos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.leothan.shoppingcenter.apis.Direcciones
import com.leothan.shoppingcenter.apis.WebViewHelper
import com.leothan.shoppingcenter.databinding.FragmentFavoritosBinding

class FavoritosFragment : Fragment() {

    private var _binding: FragmentFavoritosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favoritosViewModel =
            ViewModelProvider(this).get(FavoritosViewModel::class.java)

        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val loading = binding.layoutWeb.layoutLoading.loading
        loading.isVisible = true
        val webView = binding.layoutWeb.webView
        WebViewHelper.webView(webView, Direcciones().URL_FAVORITOS, activity, loading)
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