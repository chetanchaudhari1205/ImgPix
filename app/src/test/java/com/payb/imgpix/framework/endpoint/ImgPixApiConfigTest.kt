package com.payb.imgpix.framework.endpoint

import android.content.Context
import android.content.res.AssetManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*
import java.io.InputStream
import java.util.*

/**
 * ImgPixApiConfigTest test class
 */
@RunWith(MockitoJUnitRunner::class)
class ImgPixApiConfigTest {

    private lateinit var imgPixApiConfig: ImgPixApiConfig
    private val keyImgPixBaseUrl = "img_pix_base_url"
    private val keyImgPixApiKey = "img_pix_api_key"
    private val propertyFile = "app_config.properties"

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var assetManager: AssetManager

    @Mock
    lateinit var inputStream: InputStream
    @Mock
    lateinit var properties: Properties

    @Before
    fun setUp() {
        imgPixApiConfig = spy(ImgPixApiConfig(context))
    }

    @Test
    fun testGetImgPixApiParam() {
        doReturn(properties).whenever(imgPixApiConfig).readEnvironmentProperties()
        doReturn("keyImgPixBaseUrl").whenever(properties).getProperty(keyImgPixBaseUrl)
        doReturn("keyImgPixApiKey").whenever(properties).getProperty(keyImgPixApiKey)
        imgPixApiConfig.getImgPixApiParam()
        verify(properties, times(2)).getProperty(ArgumentMatchers.anyString())
    }

    @Test
    fun testGetImgPixApiParamException() {
        doReturn(properties).whenever(imgPixApiConfig).readEnvironmentProperties()
        imgPixApiConfig.getImgPixApiParam()
        verify(properties, times(1)).getProperty(ArgumentMatchers.anyString())
    }

    @Test
    fun testReadEnvironmentProperties() {
        doReturn(assetManager).whenever(context).assets
        doReturn(inputStream).whenever(assetManager).open(propertyFile)
        Assert.assertNotNull(imgPixApiConfig.readEnvironmentProperties())
    }
}