package com.payb.imgpix.framework.repository

import android.content.Context
import com.payb.imgpix.framework.service.ImgPixServiceContract
import com.payb.imgpix.framework.service.datamodels.ImgPixResponse
import io.reactivex.Single
import javax.inject.Inject

/**
 * ImgPixRepository class to decide whether to fetch the data from the remote server or
 * local database
 */
class ImgPixRepository @Inject constructor(
    private val imgPixService: ImgPixServiceContract,
    val context: Context
) : ImgPixRepositoryContract {

    override fun fetchImages(searchString: String, page: Int): Single<ImgPixResponse> {
        return imgPixService.fetchImages(searchString, page)
    }
}