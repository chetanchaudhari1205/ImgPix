package com.payb.imgpix.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.payb.imgpix.R
import com.payb.imgpix.databinding.ItemImageBinding
import com.payb.imgpix.databinding.ItemTagBinding
import com.payb.imgpix.framework.service.datamodels.Hit
import com.payb.imgpix.framework.viewmodel.datamodels.HitModel


class ImagesAdapter(private val context: Context, private val fragment: Fragment) :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    private val item: Int = 0
    private val loading: Int = 1

    var listImages: MutableList<HitModel> = ArrayList()

    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = getItemImageBinding(parent)
        return ViewHolder(binding)
    }

    fun getItemImageBinding(parent: ViewGroup): ItemImageBinding {
        return ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    fun addAll(listHits: MutableList<HitModel>) {
        for (hit in listHits) {
            add(hit)
        }
    }

    private fun add(hit: HitModel) {
        listImages.add(hit)
        notifyItemInserted(listImages.size - 1)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hit: HitModel = listImages[position]

        with(holder) {
            binding.userName.text = hit.user

            /*Glide
                .with(context)
                .load(hit.previewURL)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.image)*/

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