package com.payb.imgpix.framework.service.datamodels


import com.squareup.moshi.Json

/**
 * The [ImgPixResponse] from the API call to fetch the images
 */
class ImgPixResponse(
    @Json(name = "hits")
    var hits: MutableList<Hit>,
    @Json(name = "total")
    val total: Int,
    @Json(name = "totalHits")
    val totalHits: Int
)