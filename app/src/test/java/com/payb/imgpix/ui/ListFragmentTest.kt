package com.payb.imgpix.ui

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.payb.imgpix.R
import com.payb.imgpix.databinding.FragmentListBinding
import com.payb.imgpix.di.component.ImgPixComponent
import com.payb.imgpix.framework.viewmodel.ImgPixViewModel
import com.payb.imgpix.framework.viewmodel.datamodels.HitModel
import com.payb.imgpix.framework.viewmodel.datamodels.ImgPixModel
import com.payb.imgpix.utils.DialogFactory
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

/**
 * The ListFragmentTest class
 */
@RunWith(MockitoJUnitRunner::class)
class ListFragmentTest {

    private lateinit var listFragment: ListFragment

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var detailFragment: DetailFragment

    @Mock
    lateinit var fragmentListBinding: FragmentListBinding

    @Mock
    lateinit var layoutInflater: LayoutInflater

    @Mock
    lateinit var container: ViewGroup

    @Mock
    lateinit var bundle: Bundle

    @Mock
    lateinit var rootView: ConstraintLayout

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var mainActivity: MainActivity

    @Mock
    lateinit var imgPixComponent: ImgPixComponent

    @Mock
    lateinit var view: View

    @Mock
    lateinit var imgPixViewModel: ImgPixViewModel

    @Mock
    lateinit var imgPixModel: ImgPixModel

    @Mock
    lateinit var scheduler: Scheduler

    @Mock
    lateinit var imagesAdapter: ImagesAdapter

    @Mock
    lateinit var dialogPositiveOnClickListener: DialogInterface.OnClickListener

    @Mock
    lateinit var dialogNegativeOnClickListener: DialogInterface.OnClickListener

    @Mock
    lateinit var alertDialog: AlertDialog

    @Mock
    lateinit var dialogFactory: DialogFactory

    @Mock
    lateinit var hitModel: HitModel

    @Mock
    lateinit var fragmentManager: FragmentManager

    @Mock
    lateinit var fragmentTransaction: FragmentTransaction

    @Mock
    lateinit var menu: Menu

    @Mock
    lateinit var menuInflater: MenuInflater

    @Mock
    lateinit var menuItem: MenuItem

    @Mock
    lateinit var searchView: SearchView

    @Captor
    lateinit var argumentCaptor: ArgumentCaptor<SearchView.OnQueryTextListener>

    @Before
    fun setup() {
        listFragment = spy(ListFragment())
        listFragment.binding = fragmentListBinding
        listFragment.imgPixViewModel = imgPixViewModel
        listFragment.adapter = imagesAdapter
        listFragment.alertDialog = alertDialog
        listFragment.dialogFactory = dialogFactory
    }

    @Test
    fun testOnCreateView() {
        doReturn(fragmentListBinding).whenever(listFragment)
            .getBinding(layoutInflater, container, false)
        doReturn(rootView).whenever(fragmentListBinding).root
        Assert.assertNotNull(
            listFragment.onCreateView(
                layoutInflater,
                container,
                bundle
            )
        )
        verify(listFragment, times(1)).getBinding(layoutInflater, container, false)
    }

    @Test
    fun testOnCreate() {
        doNothing().whenever(listFragment).superOnCreate(bundle)
        listFragment.onCreate(bundle)
        verify(listFragment, times(1)).setHasOptionsMenu(true)
    }

    @Test
    fun testOnAttach() {
        doReturn(mainActivity).whenever(listFragment).activity
        doReturn(imgPixComponent).whenever(mainActivity).getActivityComponent()
        listFragment.onAttach(context)
        verify(imgPixComponent, times(1)).inject(listFragment)
    }

    @Test
    fun testOnViewCreated() {
        doNothing().whenever(listFragment).initialiseRecyclerView()
        doNothing().whenever(listFragment).subscribeToFetchImages("fruits", 1)
        listFragment.onViewCreated(view, bundle)
        verify(listFragment, times(1)).initialiseRecyclerView()
    }

    @Test
    fun testUpdateUI() {
        listFragment.updateUI(imgPixModel)
        verify(imagesAdapter).addAll(ArgumentMatchers.anyList())
    }

    @Test
    fun testDisplayConfirmationDialog() {
        doReturn("Hey there..").whenever(listFragment).getString(R.string.dialog_title)
        doReturn("Would you like to see some more details?").whenever(listFragment)
            .getString(R.string.dialog_description)
        doReturn("Yes please!").whenever(listFragment).getString(R.string.yes)
        doReturn("Not now").whenever(listFragment).getString(R.string.no)
        doReturn(dialogPositiveOnClickListener).whenever(listFragment)
            .getYesButtonClickListener(hitModel)
        doReturn(dialogNegativeOnClickListener).whenever(listFragment).getNoButtonClickListener()
        doReturn(context).whenever(listFragment).context
        doReturn(alertDialog).whenever(dialogFactory).createAlertMaterialDialog(
            context,
            "Hey there..",
            "Would you like to see some more details?",
            "Yes please!",
            "Not now",
            dialogPositiveOnClickListener,
            dialogNegativeOnClickListener
        )
        doReturn(mainActivity).whenever(listFragment).activity
        listFragment.displayConfirmationDialog(hitModel)
        verify(dialogFactory, times(1)).showDialog(alertDialog)
    }

    @Test
    fun testPositiveClickListener() {
        doReturn(detailFragment).whenever(listFragment).getDetailFragmentInstance()
        doReturn(bundle).whenever(listFragment).getBundleInstance()
        doReturn(fragmentManager).whenever(listFragment).parentFragmentManager
        doReturn(fragmentTransaction).whenever(fragmentManager).beginTransaction()
        doReturn(fragmentTransaction).whenever(fragmentTransaction)
            .add(R.id.nav_host_fragment, detailFragment)
        doReturn(fragmentTransaction).whenever(fragmentTransaction).addToBackStack("backStack")
        val listener = listFragment.getYesButtonClickListener(hitModel)
        listener.onClick(alertDialog, 1)
        verify(dialogFactory, times(1)).dismissDialog(alertDialog)
        verify(fragmentTransaction, times(1)).commit()
    }

    @Test
    fun testNegativeClickListener() {
        val listener = listFragment.getNoButtonClickListener()
        listener.onClick(alertDialog, 1)
        verify(dialogFactory, times(1)).dismissDialog(alertDialog)
    }

    @Test
    fun testGetOnQueryTextListener() {
        Mockito.doReturn(Single.just(imgPixModel))
            .whenever(imgPixViewModel).fetchImages("fruits", 1)

        doReturn(scheduler).whenever(listFragment).getScheduler()

        val observable = imgPixViewModel.fetchImages("fruits", 1).toObservable()
        val imgPixObserver = TestObserver.create<ImgPixModel>()
        observable.subscribe(imgPixObserver)
        imgPixObserver.assertNoErrors()

        listFragment.subscribeToFetchImages("fruits", 1)

        imgPixObserver.values()[0].run {
            Assert.assertEquals(this.totalHits, imgPixModel.totalHits)
        }

        doReturn(context).whenever(listFragment).requireContext()
        doReturn("fruits").whenever(listFragment).encodeSearchQuery("fruits")
        doNothing().whenever(listFragment).subscribeToFetchImages("fruits", 1)
        doNothing().whenever(listFragment).setBindingToRecyclerView()

        val queryTextListener = listFragment.getOnQueryTextListener(menuItem, searchView)
        Assert.assertTrue(queryTextListener.onQueryTextSubmit("fruits"))
        verify(listFragment, times(2)).subscribeToFetchImages("fruits", 1)
    }

    @Test
    fun testOnCreateOptionsMenu() {
        argumentCaptor = ArgumentCaptor.forClass(
            SearchView.OnQueryTextListener::class.java
        )
        doNothing().whenever(searchView).setOnQueryTextListener(argumentCaptor.capture())
        doReturn(menuItem).whenever(menu).findItem(R.id.search_bar)
        doReturn(searchView).whenever(menuItem).actionView
        doReturn(context).whenever(listFragment).requireContext()
        doNothing().whenever(listFragment).setBindingToRecyclerView()
        doNothing().whenever(listFragment).subscribeToFetchImages("fruits", 1)

        listFragment.onCreateOptionsMenu(menu, menuInflater)
        argumentCaptor.value.onQueryTextSubmit("fruits")
        verify(menuItem, times(1)).collapseActionView()
    }
}