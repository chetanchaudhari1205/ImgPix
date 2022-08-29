package com.payb.imgpix.framework.viewmodel

import com.payb.imgpix.framework.repository.ImgPixRepositoryContract
import com.payb.imgpix.framework.service.datamodels.ImgPixResponse
import com.payb.imgpix.framework.viewmodel.datamodels.HitModel
import com.payb.imgpix.framework.viewmodel.datamodels.ImgPixModel
import io.reactivex.Single
import javax.inject.Inject

/**
 * The ImgPixViewModel ViewModel class
 */
class ImgPixViewModel @Inject constructor(private val imgPixRepository: ImgPixRepositoryContract) :
    ImgPixViewModelContract {

    override fun fetchImages(searchQuery: String, page: Int): Single<ImgPixModel> =
        imgPixRepository.fetchImages(searchQuery, page).map {
            createConsumableModels(it)
        }

    fun createConsumableModels(imgPixResponse: ImgPixResponse): ImgPixModel {

        val listHitModel = mutableListOf<HitModel>()
        for (hit in imgPixResponse.hits) {
            val hitModel = HitModel(hit.id)
            hitModel.comments = hit.comments
            hitModel.downloads = hit.downloads
            hitModel.largeImageURL = hit.largeImageURL
            hitModel.likes = hit.likes
            hitModel.previewURL = hit.previewURL
            hitModel.tags = hit.tags
            hitModel.user = hit.user
            listHitModel.add(hitModel)
        }

        return ImgPixModel(listHitModel, imgPixResponse.total, imgPixResponse.totalHits)
    }
}