package com.payb.imgpix.framework.viewmodel

import com.payb.imgpix.framework.viewmodel.datamodels.ImgPixModel
import io.reactivex.Single

/**
 * The ImgPixViewModelContract interface
 */
interface ImgPixViewModelContract {

    /**
     * Method to fetch images so that they can be consumed by the Views
     * @param searchQuery [String]
     * @param page [Int] to have page wise response for pagination in the app
     * @return Single<ImgPixModel>
     */
    fun fetchImages(searchQuery: String, page: Int): Single<ImgPixModel>
}