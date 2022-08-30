package com.payb.imgpix.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.payb.imgpix.R
import com.payb.imgpix.databinding.FragmentListBinding
import com.payb.imgpix.framework.viewmodel.ImgPixViewModelContract
import com.payb.imgpix.framework.viewmodel.datamodels.HitModel
import com.payb.imgpix.framework.viewmodel.datamodels.ImgPixModel
import com.payb.imgpix.utils.DialogFactory
import com.payb.imgpix.utils.PaginationScrollListener
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import java.net.URLEncoder
import javax.inject.Inject

/**
 * List screen to display the following:
 * 1. A thumbnail of the image
 * 2. The Pixabay user name
 * 3. A list of image’s tag
 */
class ListFragment : Fragment() {

    private val pageStart: Int = 1
    private var isLastPage: Boolean = false
    private var totalPages: Int = 1
    private var currentPage: Int = pageStart
    private var searchQuery = "fruits"

    lateinit var adapter: ImagesAdapter
    lateinit var alertDialog: AlertDialog
    lateinit var binding: FragmentListBinding

    @Inject
    lateinit var imgPixViewModel: ImgPixViewModelContract

    @Inject
    lateinit var dialogFactory: DialogFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getBinding(inflater, container, false)
        return binding.root
    }

    /**
     * Method to get the [FragmentListBinding] for the ListFragment
     * @param inflater the layout inflater
     * @param container the view group
     * @param b whether to attach to the parent or not
     * @return [FragmentListBinding]
     */
    fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Boolean
    ): FragmentListBinding {
        return FragmentListBinding.inflate(inflater, container, b)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseRecyclerView()
        // When the app starts it should trigger a search for the string “fruits”
        subscribeToFetchImages(searchQuery, currentPage)
    }

    /**
     * Method to initialize and populate recycler view with the data received from the ViewModel
     */
    fun initialiseRecyclerView() {
        adapter = ImagesAdapter(requireContext(), this)
        binding.recyclerViewImages.adapter = adapter
        binding.recyclerViewImages.layoutManager = GridLayoutManager(context, 2)

        binding.recyclerViewImages.addOnScrollListener(object :
            PaginationScrollListener(binding.recyclerViewImages.layoutManager as GridLayoutManager) {
            override fun loadMoreItems() {
                currentPage += 1

                Handler(Looper.myLooper()!!).postDelayed({
                    subscribeToFetchImages(searchQuery, currentPage)
                }, 1000)
            }

            override fun getTotalPageCount(): Int {
                return totalPages
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

        })
    }

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        superOnCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    fun superOnCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.search_bar)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(getOnQueryTextListener())
    }

    /**
     * Method to get the on search query text listener instance to receive the events on query text submit
     * @return [SearchView.OnQueryTextListener]
     */
    fun getOnQueryTextListener(): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchQuery = query
                currentPage = pageStart
                adapter = ImagesAdapter(requireContext(), this@ListFragment)
                setBindingToRecyclerView()
                subscribeToFetchImages(encodeSearchQuery(query), currentPage)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        }
    }

    /**
     * Method to URL encode the search query as required by the Pixabay API documentation
     * @param query the search query
     * @return URL encoded search string
     */
    fun encodeSearchQuery(query: String): String {
        return URLEncoder.encode(query, "utf-8")
    }

    fun setBindingToRecyclerView() {
        binding.recyclerViewImages.adapter = adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).getActivityComponent().inject(this)
    }

    /**
     * Method to subscribe for fetching images from the Pixabay API
     * @param searchString the search query
     * @param page used for fetching the page wise response for pagination purpose
     */
    @SuppressLint("CheckResult")
    fun subscribeToFetchImages(searchString: String, page: Int) {
        imgPixViewModel.fetchImages(searchString, page)
            .observeOn(getScheduler())
            .subscribe({
                updateUI(it)
            }, {
                Log.e(TAG, " Exception : ${it.printStackTrace()}")
            })
    }

    /**
     * Method to fetch the Scheduler instance
     * @return [Scheduler]
     */
    fun getScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    /**
     * Method to update the UI view components with the data from the ViewModel
     * @param imgPixModel [ImgPixModel]
     */
    fun updateUI(imgPixModel: ImgPixModel) {
        totalPages = imgPixModel.totalHits
        if (currentPage > totalPages)
            isLastPage = true
        adapter.addAll(imgPixModel.hits)
    }

    /**
     * Method to navigate to the [DetailFragment]
     * @param hit [HitModel]
     */
    private fun navigateToDetailFragment(hit: HitModel) {
        val detailFragment = getDetailFragmentInstance()
        val bundle = getBundleInstance()
        bundle.putSerializable(HIT_MODEL, hit)
        detailFragment.arguments = bundle
        val fragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.nav_host_fragment, detailFragment).addToBackStack("backStack")
            .commit()
    }

    /**
     * Method to get the bundle instance
     * @return [Bundle]
     */
    fun getBundleInstance(): Bundle {
        return Bundle()
    }

    /**
     * Method to get the [DetailFragment] instance
     * @return DetailFragment
     */
    fun getDetailFragmentInstance(): DetailFragment {
        return DetailFragment()
    }

    /**
     * Method to display the confirmation dialog
     * @param hit [HitModel]
     */
    fun displayConfirmationDialog(hit: HitModel) {
        alertDialog = dialogFactory.createAlertMaterialDialog(
            context,
            getString(R.string.dialog_title),
            getString(R.string.dialog_description),
            getString(R.string.yes),
            getString(R.string.no),
            getYesButtonClickListener(hit),
            getNoButtonClickListener()
        )

        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setOwnerActivity(this.activity as Activity)
        dialogFactory.showDialog(alertDialog)
    }

    /**
     * Method to get the positive button click listener instance
     * @param hit [HitModel]
     * @return [DialogInterface.OnClickListener]
     */
    fun getYesButtonClickListener(hit: HitModel): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { _, _ ->
            let {
                navigateToDetailFragment(hit)
                dialogFactory.dismissDialog(alertDialog)
            }
        }
    }

    /**
     * Method to get the negative button click listener instance
     * @return [DialogInterface.OnClickListener]
     */
    fun getNoButtonClickListener(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { _, _ ->
            let {
                dialogFactory.dismissDialog(alertDialog)
            }
        }
    }

    companion object {
        const val HIT_MODEL = "hitModel"
        private val TAG = ListFragment::class.java.simpleName
    }
}