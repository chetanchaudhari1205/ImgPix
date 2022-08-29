package com.payb.imgpix.di

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.payb.imgpix.di.module.ImgPixModule
import com.payb.imgpix.framework.endpoint.ImgPixApiConfig
import com.payb.imgpix.framework.endpoint.ImgPixApiUrl
import com.payb.imgpix.framework.repository.ImgPixRepositoryContract
import com.payb.imgpix.framework.service.ImgPixApi
import com.payb.imgpix.framework.service.ImgPixServiceContract
import okhttp3.Cache
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*
import java.io.File

/**
 * ImgPixModuleTest test class
 */
@RunWith(MockitoJUnitRunner::class)
class ImgPixModuleTest {

    private lateinit var imgPixModule: ImgPixModule

    @Mock
    lateinit var activity: Activity

    @Mock
    lateinit var imgPixRepositoryContract: ImgPixRepositoryContract

    @Mock
    lateinit var imgPixServiceContract: ImgPixServiceContract

    @Mock
    lateinit var imgPixApiUrl: ImgPixApiUrl

    @Mock
    lateinit var imgPixApi: ImgPixApi

    @Mock
    lateinit var imgPixApiConfig: ImgPixApiConfig

    @Mock
    lateinit var file: File

    @Mock
    lateinit var cache: Cache

    @Mock
    lateinit var connectivityManager: ConnectivityManager

    @Mock
    lateinit var networkInfo: NetworkInfo

    @Before
    fun setup() {
        imgPixModule = spy(ImgPixModule(activity))
    }

    @Test
    fun testProvideContext() {
        Assert.assertEquals(activity, imgPixModule.provideContext())
    }

    @Test
    fun testProvideActivity() {
        Assert.assertEquals(activity, imgPixModule.provideActivity())
    }

    @Test
    fun testProvideDialogFactory() {
        Assert.assertNotNull(imgPixModule.provideDialogFactory())
    }

    @Test
    fun testProvideImgPixViewModel() {
        Assert.assertNotNull(imgPixModule.provideImgPixViewModel(imgPixRepositoryContract))
    }

    @Test
    fun testProvideImgPixRepository() {
        Assert.assertNotNull(imgPixModule.provideImgPixRepository(imgPixServiceContract))
    }

    @Test
    fun testProvideImgPixService() {
        Assert.assertNotNull(imgPixModule.provideImgPixService(imgPixApiUrl, imgPixApi))
    }

    @Test
    fun testProvidesImgPixApiUrl() {
        imgPixModule.providesImgPixApiUrl(imgPixApiConfig)
        verify(imgPixApiConfig, times(1)).getImgPixApiParam()
    }

    @Test
    fun testProvidesImgPixApiConfig() {
        Assert.assertNotNull(imgPixModule.providesImgPixApiConfig())
    }

    @Test
    fun testCreateCache() {
        doReturn(cache).whenever(imgPixModule).getCache((5 * 1024 * 1024).toLong())
        Assert.assertNotNull(imgPixModule.createCache())
    }

    @Test
    fun testHasNetworkTrue() {
        doReturn(activity).whenever(imgPixModule).provideContext()
        doReturn(connectivityManager).whenever(activity)
            .getSystemService(Context.CONNECTIVITY_SERVICE)
        doReturn(networkInfo).whenever(connectivityManager).activeNetworkInfo
        doReturn(true).whenever(networkInfo).isConnected
        Assert.assertTrue(imgPixModule.hasNetwork())
    }

    @Test
    fun testHasNetworkFalse() {
        doReturn(activity).whenever(imgPixModule).provideContext()
        doReturn(connectivityManager).whenever(activity)
            .getSystemService(Context.CONNECTIVITY_SERVICE)
        doReturn(networkInfo).whenever(connectivityManager).activeNetworkInfo
        doReturn(false).whenever(networkInfo).isConnected
        Assert.assertFalse(imgPixModule.hasNetwork())
    }
}