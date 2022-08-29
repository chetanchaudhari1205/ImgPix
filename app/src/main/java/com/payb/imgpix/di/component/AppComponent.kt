package com.payb.imgpix.di.component

import android.app.Application
import android.content.Context
import com.payb.imgpix.ImgPixApplication
import com.payb.imgpix.di.annotation.ApplicationContext
import com.payb.imgpix.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface AppComponent {

    fun inject(imgPixApplication: ImgPixApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getApplication(): Application
}