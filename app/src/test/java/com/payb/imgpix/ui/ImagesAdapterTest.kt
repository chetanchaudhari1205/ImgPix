package com.payb.imgpix.ui

import android.content.Context
import android.view.ViewGroup
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import com.payb.imgpix.databinding.ItemImageBinding
import com.payb.imgpix.framework.service.datamodels.Hit
import com.payb.imgpix.framework.viewmodel.datamodels.HitModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class)
class ImagesAdapterTest {

    private lateinit var imagesAdapter: ImagesAdapter
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock private lateinit var context: Context
    @Mock private lateinit var listFragment: Fragment
    @Mock private lateinit var itemImageBinding: ItemImageBinding
    @Mock private lateinit var viewGroup: ViewGroup
    @Mock private lateinit var rootLayout: MaterialCardView
    private lateinit var listHits: MutableList<HitModel>

    @Before
    fun setup() {
        createListOfHitModels()
        imagesAdapter = spy(ImagesAdapter(context, listFragment))
    }

    private fun createListOfHitModels() {
        listHits = mutableListOf()
        val hitModelFirst = HitModel(1)
        hitModelFirst.user = "user"
        hitModelFirst.largeImageURL = "largeImageURL"
        hitModelFirst.comments = 5
        hitModelFirst.downloads = 10
        hitModelFirst.likes = 16
        hitModelFirst.tags = "fruit, yellow"
        hitModelFirst.previewURL = "previewURL"
        listHits.add(hitModelFirst)

        val hitModelLast = HitModel(2)
        hitModelLast.user = "user2"
        hitModelLast.largeImageURL = "largeImageURL2"
        hitModelLast.comments = 8
        hitModelLast.downloads = 2
        hitModelLast.likes = 67
        hitModelLast.tags = "bird, eagle"
        listHits.add(hitModelLast)
    }

    @Test
    fun testOnCreateViewHolder() {
        doReturn(itemImageBinding).whenever(imagesAdapter).getItemImageBinding(viewGroup)
        doReturn(rootLayout).whenever(itemImageBinding).root
        Assert.assertNotNull(imagesAdapter.onCreateViewHolder(viewGroup, 0))
    }

    @Test
    fun testGetItemCount() {
        Assert.assertNotNull(imagesAdapter.itemCount)
    }

    @Test
    fun testAddAll() {
        doNothing().whenever(imagesAdapter).notifyItemInserted(anyInt())
        imagesAdapter.addAll(listHits)
        Assert.assertEquals(2, listHits.size)
    }
 }