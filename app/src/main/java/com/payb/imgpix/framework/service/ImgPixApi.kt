package com.payb.imgpix.framework.service

import com.payb.imgpix.framework.service.datamodels.ImgPixResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * The [ImgPixApi] interface to connect to server APIs
 */
interface ImgPixApi {

    /**
     * Method to connect with the remote server to fetch images from the API
     * @param url [String] base url of the API
     * @param key [String] api key
     * @param searchQuery [String]
     * @param page [Int] to fetch page wise response
     * @param imageType [String] to indicate what needs to be fetched(photo, video, etc.) for the particular searchString
     */
    @GET
    fun fetchImages(
        @Url url: String,
        @Query("key") key: String,
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("image_type") imageType: String = "photo"
    ): Single<ImgPixResponse>
}