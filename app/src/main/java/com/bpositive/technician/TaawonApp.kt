package com.bpositive.technician

import android.app.Application
import android.os.StrictMode
import com.bpositive.BuildConfig
import com.facebook.stetho.Stetho

class MvvmPattern: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

    }

}