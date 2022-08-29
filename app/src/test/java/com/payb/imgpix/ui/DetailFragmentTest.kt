package com.payb.imgpix.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.payb.imgpix.databinding.FragmentDetailBinding
import com.payb.imgpix.di.component.ImgPixComponent
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class)
class DetailFragmentTest {

    private lateinit var detailFragment: DetailFragment

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var fragmentDetailBinding: FragmentDetailBinding
    @Mock
    lateinit var layoutInflater: LayoutInflater
    @Mock
    lateinit var container: ViewGroup
    @Mock
    lateinit var bundle: Bundle
    @Mock
    lateinit var rootView: RelativeLayout
    @Mock
    lateinit var context: Context
    @Mock
    lateinit var mainActivity: MainActivity
    @Mock
    lateinit var imgPixComponent: ImgPixComponent
    @Mock
    lateinit var view: View

    @Before
    fun setup() {
        detailFragment = spy(DetailFragment())
        detailFragment.binding = fragmentDetailBinding
    }

    @Test
    fun testOnCreateView() {
        doReturn(fragmentDetailBinding).whenever(detailFragment)
            .getBinding(layoutInflater, container, false)
        doReturn(rootView).whenever(fragmentDetailBinding).root
        Assert.assertNotNull(
            detailFragment.onCreateView(
                layoutInflater,
                container,
                bundle
            )
        )
        verify(detailFragment, times(1)).getBinding(layoutInflater, container, false)
    }

    @Test
    fun testOnAttach() {
        doReturn(mainActivity).whenever(detailFragment).activity
        doReturn(imgPixComponent).whenever(mainActivity).getActivityComponent()
        detailFragment.onAttach(context)
        verify(imgPixComponent, times(1)).inject(detailFragment)
    }

    @Test
    fun testOnViewCreated() {
        /*testOnCreateView()
        doReturn(hitModel).whenever(bundle).getSerializable(HIT_MODEL)
        doReturn(requestManager).whenever(detailFragment).getGlideRequestManager()
        doReturn("largeImageURL").whenever(hitModel).largeImageURL
        whenever(requestManager.load(hitModel.largeImageURL)).thenReturn(requestBuilderDrawable)
        doReturn("user").whenever(hitModel).user
        doReturn(userTextView).whenever(fragmentDetailBinding).user*/
        doNothing().whenever(detailFragment).initialiseUi()
        detailFragment.onViewCreated(view, bundle)
        verify(detailFragment, times(1)).initialiseUi()
    }
}