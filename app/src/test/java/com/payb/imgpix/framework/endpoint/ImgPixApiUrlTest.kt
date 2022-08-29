package com.payb.imgpix.framework.endpoint

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * ImgPixApiUrlTest test class
 */
@RunWith(MockitoJUnitRunner::class)
class ImgPixApiUrlTest {
    @Test
    fun test() {
        val imgPixApiUrl = ImgPixApiUrl(
            "img_pix_api_base_url",
            "img_pix_api_key"
        )
        Assert.assertNotNull(imgPixApiUrl.imgPixApiBaseUrl)
        Assert.assertNotNull(imgPixApiUrl.imgPixApiKey)
    }
}