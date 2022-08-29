package com.payb.imgpix.framework.viewmodel.datamodels

/**
 * [ImgPixModel] data class to hold response consumable by the View
 */
data class ImgPixModel(
    var hits: MutableList<HitModel>,
    var total: Int = 0,
    var totalHits: Int = 0
)


