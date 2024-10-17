package com.dhenu.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.dhenu.app.data.local.AppPreference

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // Force light mode

        init(this)
        AppPreference.getInstance(this)
    }

    fun init(app: MyApplication) {
        instance = app
    }

    companion object {
        var instance: MyApplication? = null
            private set
            @Synchronized
            get
    }
}

