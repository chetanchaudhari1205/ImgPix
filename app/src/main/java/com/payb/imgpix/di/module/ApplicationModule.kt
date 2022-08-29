package com.payb.imgpix.di.module

import android.app.Application
import android.content.Context
import com.payb.imgpix.di.annotation.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    lateinit var application: Application

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return application
    }

    @Provides
    fun provideApplication(): Application {
        return application
    }
}