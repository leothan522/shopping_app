package com.leothan.shoppingcenter.prefs

import android.app.Application

class ShoppingCenterApplication : Application() {
    companion object{
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }

}