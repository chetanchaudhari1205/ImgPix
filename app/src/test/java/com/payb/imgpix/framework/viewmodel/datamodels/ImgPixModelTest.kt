package com.payb.imgpix.framework.viewmodel.datamodels

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * ImgPixModelTest test class
 */
@RunWith(MockitoJUnitRunner::class)
class ImgPixModelTest {

    @Mock
    lateinit var listHitModel: MutableList<HitModel>

    @Test
    fun test() {
        val imgPixModel = ImgPixModel(
            listHitModel,
            3000, 500
        )
        Assert.assertEquals(500, imgPixModel.totalHits)
        Assert.assertNotNull(imgPixModel.hits)
    }

}