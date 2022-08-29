package com.payb.imgpix.framework.viewmodel

import com.payb.imgpix.framework.repository.ImgPixRepositoryContract
import com.payb.imgpix.framework.service.datamodels.Hit
import com.payb.imgpix.framework.service.datamodels.ImgPixResponse
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

/**
 * ImgPixViewModelTest test class
 */
@RunWith(MockitoJUnitRunner::class)
class ImgPixViewModelTest {

    private lateinit var imgPixViewModel: ImgPixViewModel

    @Mock
    lateinit var imgPixRepository: ImgPixRepositoryContract

    private lateinit var imgPixResponse: ImgPixResponse

    @Before
    fun setUp() {
        imgPixViewModel = spy(ImgPixViewModel(imgPixRepository))
        createHitModel()
    }

    private fun createHitModel() {
        val listHits = mutableListOf<Hit>()
        val hit = Hit(
            12,
            5,
            10,
            1,
            320,
            1234,
            450,
            "largeImageURL",
            15,
            "pageURL",
            90,
            "previewURL",
            150,
            "fruits, yellow",
            "photo",
            "user",
            1509,
            "userImageURL",
            100,
            300,
            "webFormatURL",
            500
        )
        listHits.add(hit)
        imgPixResponse = ImgPixResponse(listHits, 3000, 500)
    }

    @Test
    fun testFetchImages() {
        doReturn(Single.just(imgPixResponse)).whenever(imgPixRepository).fetchImages("fruits", 1)

        imgPixViewModel.fetchImages("fruits", 1).test()
            .assertValue {
                it.totalHits == imgPixResponse.totalHits
            }

        verify(imgPixViewModel, times(1))
            .fetchImages("fruits", 1)
    }

    @Test
    fun testCreateConsumableModels() {
        imgPixViewModel.createConsumableModels(imgPixResponse)
        assertEquals(5, imgPixResponse.hits[0].comments)
    }
}