package com.payb.imgpix.framework.viewmodel.datamodels

import java.io.Serializable

/**
 * [HitModel] data class to hold the models consumable by the View
 */
data class HitModel(var id: Int) : Serializable {
    var comments: Int = 0
    var downloads: Int = 0
    lateinit var largeImageURL: String
    var likes: Int = 0
    lateinit var previewURL: String
    lateinit var tags: String
    lateinit var user: String
}