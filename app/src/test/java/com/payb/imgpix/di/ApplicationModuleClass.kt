package com.payb.imgpix.di

import android.app.Application
import com.payb.imgpix.di.module.ApplicationModule
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * ApplicationModuleClass test class
 */
@RunWith(MockitoJUnitRunner::class)
class ApplicationModuleClass {

    private lateinit var applicationModule: ApplicationModule

    @Mock
    lateinit var application: Application

    @Before
    fun setup() {
        applicationModule = ApplicationModule()
        applicationModule.application = application
    }

    @Test
    fun testProvideContext() {
        Assert.assertEquals(application, applicationModule.provideContext())
    }

    @Test
    fun testProvideApplication() {
        Assert.assertEquals(application, applicationModule.provideApplication())
    }
}