package com.payb.imgpix.framework.service

import com.payb.imgpix.framework.service.datamodels.ImgPixResponse
import io.reactivex.Single

/**
 * The ImgPixServiceContract interface
 */
interface ImgPixServiceContract {

    /**
     * Method to invoke API call to fetch images
     * @param searchQuery [String]
     * @param page [Int] to fetch page wise response using pagination
     * @return Single<ImgPixResponse>
     */
    fun fetchImages(searchQuery: String, page: Int): Single<ImgPixResponse>
}