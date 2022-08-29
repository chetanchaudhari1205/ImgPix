package com.payb.imgpix.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.payb.imgpix.databinding.FragmentDetailBinding
import com.payb.imgpix.framework.viewmodel.datamodels.HitModel
import com.payb.imgpix.ui.ListFragment.Companion.HIT_MODEL

/**
 * Detail screen to display the following:
 * 1. A bigger version of the image
 * 2. The name of the user
 * 3. A list of image’s tag
 * 4. The number of likes
 * 5. The number of downloads
 * 6. The number of comments
 */
class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    //var binding get() = _binding!!
    //private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getBinding(inflater, container, false)
        return binding.root
    }

    fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Boolean
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater, container, b)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseUi()
    }

    fun initialiseUi() {
        val hitModel = arguments?.getSerializable(HIT_MODEL) as HitModel
        //val hitModel = args.hitModel
        val requestManager = getGlideRequestManager()
        requestManager.load(hitModel.largeImageURL).into(binding.image)
        //Glide.with(this).load(hitModel.largeImageURL).into(binding.image)
        val userTextView = binding.user
        userTextView.text = hitModel.user
        binding.likes.text = hitModel.likes.toString()
        binding.comments.text = hitModel.comments.toString()
        binding.downloads.text = hitModel.downloads.toString()

        val tagsAdapter = TagsAdapter(hitModel.tags.split(",").toList())

        binding.recyclerViewTags.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewTags.adapter = tagsAdapter
    }

    fun getGlideRequestManager(): RequestManager {
        return Glide.with(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).getActivityComponent().inject(this)
    }

}