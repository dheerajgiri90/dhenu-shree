package com.dhenu.app

import android.app.Application
import com.dhenu.app.data.local.AppPreference

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
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

