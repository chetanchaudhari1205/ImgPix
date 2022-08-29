package com.payb.imgpix.framework.repository

import android.content.Context
import com.payb.imgpix.framework.service.ImgPixService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * ImgPixRepositoryTest test class
 */
@RunWith(MockitoJUnitRunner::class)
class ImgPixRepositoryTest {

    private lateinit var imgPixRepository: ImgPixRepository
    @Mock
    lateinit var imgPixService: ImgPixService
    @Mock
    lateinit var context: Context

    @Before
    fun setUp() {
        imgPixRepository = ImgPixRepository(imgPixService, context)
    }

    @Test
    fun testFetchImages() {
        imgPixRepository.fetchImages("fruits", 1)
        verify(imgPixService, times(1)).fetchImages("fruits", 1)
    }
}