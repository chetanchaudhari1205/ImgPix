package com.payb.imgpix.ui

import android.os.Bundle
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.payb.imgpix.ImgPixApplication
import com.payb.imgpix.di.component.AppComponent
import com.payb.imgpix.di.component.ImgPixComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class)
class MainActivityTest {

    private lateinit var mainActivity: MainActivity

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var bundle: Bundle
    @Mock
    lateinit var imgPixComponent: ImgPixComponent
    @Mock
    lateinit var appComponent: AppComponent
    @Mock
    lateinit var imgPixApplication: ImgPixApplication

    @Before
    fun setup() {
        mainActivity = spy(MainActivity())
    }

    @Test
    fun testOnCreate() {
        doNothing().whenever(mainActivity).superOnCreate(anyOrNull())
        doNothing().whenever(mainActivity).setContentView(ArgumentMatchers.anyInt())
        doReturn(imgPixComponent).whenever(mainActivity).getActivityComponent()
        mainActivity.onCreate(bundle)
        verify(imgPixComponent, times(1)).inject(mainActivity)
    }

    @Test
    fun testGetActivityComponent() {
        doReturn(imgPixApplication).whenever(mainActivity).getApplicationInstance()
        doReturn(appComponent).whenever(imgPixApplication).getComponent()
        mainActivity.getActivityComponent()
    }
}