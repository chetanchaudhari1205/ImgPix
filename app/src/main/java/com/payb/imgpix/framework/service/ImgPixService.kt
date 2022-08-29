package com.payb.imgpix.framework.service

import com.payb.imgpix.framework.endpoint.ImgPixApiUrl
import com.payb.imgpix.framework.service.datamodels.ImgPixResponse
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * The [ImgPixService] class to fetch data from the remote server
 */
class ImgPixService @Inject constructor(
    private var imgPixApiUrl: ImgPixApiUrl,
    private val imgPixApi: ImgPixApi
) : ImgPixServiceContract {

    override fun fetchImages(searchQuery: String, page: Int): Single<ImgPixResponse> {

        return imgPixApi.fetchImages(
            imgPixApiUrl.imgPixApiBaseUrl,
            imgPixApiUrl.imgPixApiKey,
            searchQuery,
            page
        )
            .subscribeOn(getScheduler())
    }

    private fun getScheduler(): Scheduler {
        return Schedulers.io()
    }
}