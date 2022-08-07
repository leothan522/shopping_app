package com.leothan.shoppingcenter

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.leothan.shoppingcenter.databinding.ActivityMainBinding
import com.leothan.shoppingcenter.databinding.NavHeaderMainBinding
import com.leothan.shoppingcenter.prefs.ShoppingCenterApplication.Companion.prefs

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_principal,
                R.id.nav_categorias,
                R.id.nav_tiendas,
                R.id.nav_favoritos,
                R.id.nav_carrito,
                R.id.nav_pedidos,
                R.id.nav_perfil,
                R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Header
        val viewHeader = navView.getHeaderView(0)
        val header = NavHeaderMainBinding.bind(viewHeader)
        header.tvNameMain.text = prefs.getName()
        header.tvEmailMain.text = prefs.getEmail()

        //bottom nav view
        val bottom: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        bottom.setupWithNavController(navController)

        //Color Barra Notificaciones
        setStatusBarColor(getColor(R.color.primaryLightColor))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun Activity.setStatusBarColor(@ColorInt color: Int) {
        val window = getWindow()
        val decorView: View = window.getDecorView()
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = ColorUtils.calculateLuminance(color) > 0.5
        window.statusBarColor = color
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_cerrar -> {
            prefs.wipe()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }


}