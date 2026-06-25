package com.example.internet

import android.app.Application
import com.example.internet.data.AppContainer
import com.example.internet.data.DefaultAppContainer

class ProductApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
