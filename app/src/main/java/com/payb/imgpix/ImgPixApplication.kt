package com.payb.imgpix

import android.app.Application
import android.content.Context
import com.payb.imgpix.di.component.AppComponent
import com.payb.imgpix.di.component.DaggerAppComponent
import com.payb.imgpix.di.module.ApplicationModule

/**
 * The ImgPixApplication class
 */
class ImgPixApplication : Application() {

    private lateinit var applicationComponent: AppComponent

    companion object {

        fun get(context: Context): ImgPixApplication {
            return context.applicationContext as ImgPixApplication
        }

    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerAppComponent
            .builder()
            .applicationModule(ApplicationModule())
            .build()
        applicationComponent.inject(this)
    }

    fun getComponent(): AppComponent {
        return applicationComponent
    }
}