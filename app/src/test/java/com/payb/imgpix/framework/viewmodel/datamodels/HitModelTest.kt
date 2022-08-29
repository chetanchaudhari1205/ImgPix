package com.payb.imgpix.framework.viewmodel.datamodels

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * HitModelTest test class
 */
@RunWith(MockitoJUnitRunner::class)
class HitModelTest {

    @Test
    fun test() {
        val hitModel = HitModel(1)
        hitModel.user = "user"
        Assert.assertEquals(1, hitModel.id)
        Assert.assertEquals("user", hitModel.user)
    }
}