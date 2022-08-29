package com.payb.imgpix.framework.repository

import com.payb.imgpix.framework.service.datamodels.ImgPixResponse
import io.reactivex.Single

/**
 * The ImgPixRepositoryContract interface
 */
interface ImgPixRepositoryContract {

    /**
     * Method to fetch images either from remote server or local database if present
     * @param searchString [String]
     * @param page [Int] to indicate page wise response to handle pagination
     * @return Single<ImgPixResponse>
     */
    fun fetchImages(searchString: String, page: Int): Single<ImgPixResponse>
}