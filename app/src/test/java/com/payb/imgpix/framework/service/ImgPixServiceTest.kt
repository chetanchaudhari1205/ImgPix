package com.payb.imgpix.framework.service

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.payb.imgpix.framework.endpoint.ImgPixApiUrl
import com.payb.imgpix.framework.service.datamodels.Hit
import com.payb.imgpix.framework.service.datamodels.ImgPixResponse
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.spy
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

/**
 * ImgPixServiceTest test class
 */
@RunWith(MockitoJUnitRunner::class)
class ImgPixServiceTest {

    private lateinit var imgPixService: ImgPixService

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var imgPixApiUrl: ImgPixApiUrl

    @Mock
    lateinit var imgPixApi: ImgPixApi

    private lateinit var imgPixResponse: ImgPixResponse

    @Before
    fun setUp() {
        createHitModel()
        imgPixService = spy(ImgPixService(imgPixApiUrl, imgPixApi))
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
        doReturn(Single.just(imgPixResponse)).whenever(imgPixService).fetchImages("fruits", 1)

        val observable = imgPixService.fetchImages("fruits", 1).toObservable()
        val imgPixObserver = TestObserver.create<ImgPixResponse>()
        observable.subscribe(imgPixObserver)
        imgPixObserver.assertNoErrors()

        imgPixObserver.values()[0].run {
            assertEquals(this.totalHits, imgPixResponse.totalHits)
        }
    }
}