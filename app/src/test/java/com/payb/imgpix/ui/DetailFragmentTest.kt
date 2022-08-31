package com.payb.imgpix.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.payb.imgpix.R
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

/**
 * DetailFragmentTest test class
 */
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

    @Mock
    lateinit var menu: Menu

    @Mock
    lateinit var menuItem: MenuItem

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
        doNothing().whenever(detailFragment).initialiseUi()
        detailFragment.onViewCreated(view, bundle)
        verify(detailFragment, times(1)).initialiseUi()
    }

    @Test
    fun testOnPrepareOptionsMenu() {
        doReturn(menuItem).whenever(menu).findItem(R.id.search_bar)
        doReturn(false).whenever(menuItem).isVisible
        detailFragment.onPrepareOptionsMenu(menu)
        Assert.assertEquals(false, menuItem.isVisible)
    }

    @Test
    fun testOnPrepareOptionsMenuNullMenuItem() {
        doReturn(null).whenever(menu).findItem(R.id.search_bar)
        detailFragment.onPrepareOptionsMenu(menu)
        verify(menuItem, never()).isVisible = false
    }
}