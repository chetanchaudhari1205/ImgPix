package com.payb.imgpix.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.payb.imgpix.databinding.ItemTagBinding

/**
 * The TagsAdapter class to populate the items on the recycler view that are received from the ViewModel
 */
class TagsAdapter(private val listTags: List<String>) :
    RecyclerView.Adapter<TagsAdapter.ViewHolder>() {

    lateinit var binding: ItemTagBinding

    inner class ViewHolder(val binding: ItemTagBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = getItemTagBinding(parent)
        return ViewHolder(binding)
    }

    /**
     * Method to get the [ItemTagBinding] that is used to achieve view binding for the UI components on the adapter
     * @param parent the ViewGroup
     * @return [ItemTagBinding]
     */
    fun getItemTagBinding(parent: ViewGroup): ItemTagBinding {
        return ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tag: String = listTags[position]
        setBindingToUIComponents(holder, tag)
    }

    fun setBindingToUIComponents(holder: ViewHolder, tag: String) {
        holder.binding.tagName.text = tag
    }

    override fun getItemCount(): Int {
        return listTags.size
    }

}