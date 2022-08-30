package com.payb.imgpix.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.payb.imgpix.databinding.ItemImageBinding
import com.payb.imgpix.framework.viewmodel.datamodels.HitModel

/**
 * The ImagesAdapter class to populate the items on the recycler view that are received from the ViewModel
 */
class ImagesAdapter(private val context: Context, private val fragment: Fragment) :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    private var listImages: MutableList<HitModel> = ArrayList()

    inner class ViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = getItemImageBinding(parent)
        return ViewHolder(binding)
    }

    /**
     * Method to get the [ItemImageBinding] that is used to achieve view binding for the UI components on the adapter
     * @param parent the ViewGroup
     * @return [ItemImageBinding]
     */
    fun getItemImageBinding(parent: ViewGroup): ItemImageBinding {
        return ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    /**
     * Method to add the list of [HitModel] received from the ViewModel to the list of HitModel on the adapter
     * @param listHits the list of HitModel
     */
    fun addAll(listHits: MutableList<HitModel>) {
        for (hit in listHits) {
            add(hit)
        }
    }

    /**
     * Method to add new [HitModel] to the list of HitModel one by one
     * @param hit the HitModel to be added to the list
     */
    private fun add(hit: HitModel) {
        listImages.add(hit)
        notifyItemInserted(listImages.size - 1)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hit: HitModel = listImages[position]

        with(holder) {
            binding.userName.text = hit.user
            Glide.with(context).load(hit.previewURL).into(binding.image)
        }

        val listTags = hit.tags.split(",").toList()
        val tagsAdapter = TagsAdapter(listTags)

        holder.binding.recyclerViewTags.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.binding.recyclerViewTags.adapter = tagsAdapter

        holder.binding.root.setOnClickListener {
            (fragment as ListFragment).displayConfirmationDialog(hit)
        }
    }

    override fun getItemCount(): Int {
        return listImages.size
    }

}