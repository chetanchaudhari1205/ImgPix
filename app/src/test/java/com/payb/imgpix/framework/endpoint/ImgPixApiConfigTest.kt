package com.payb.imgpix.framework.endpoint

import android.content.Context
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * ImgPixApiConfigTest test class
 */
@RunWith(MockitoJUnitRunner::class)
class ImgPixApiConfigTest {

    private lateinit var imgPixApiConfig: ImgPixApiConfig
    @Mock
    lateinit var context: Context

    @Before
    fun setUp() {
        imgPixApiConfig = ImgPixApiConfig(context)
    }

}