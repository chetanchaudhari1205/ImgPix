package com.payb.imgpix.framework.endpoint

import android.content.Context
import java.io.IOException
import java.util.*

/**
 * The configuration class to fetch the stored config values
 */
class ImgPixApiConfig(private val context: Context) {

    // AppConfig file path
    private val propertyFile = "app_config.properties"

    // Environment parameters
    private val keyImgPixBaseUrl = "img_pix_base_url"
    private val keyImgPixApiKey = "img_pix_api_key"

    private val emptyValue = ""

    /**
     * Method to fetch [ImgPixApiConfig] parameters
     * @return [ImgPixApiUrl]
     */
    fun getImgPixApiParam(): ImgPixApiUrl = readEnvironmentProperties().run {

        var imgPixBaseUrl = emptyValue
        var imgPixApiKey = emptyValue

        try {
            imgPixBaseUrl = getProperty(keyImgPixBaseUrl)
            imgPixApiKey = getProperty(keyImgPixApiKey)
        } catch (e: Exception) {

        }

        ImgPixApiUrl(imgPixBaseUrl, imgPixApiKey)
    }

    /**
     * This method reads the .properties file
     * @return instance of properties with contains configurations of type [Properties]
     */
    private fun readEnvironmentProperties(): Properties {
        val properties = Properties()
        try {
            properties.load(context.assets.open(propertyFile))
        } catch (e: IOException) {

        }
        return properties
    }
}