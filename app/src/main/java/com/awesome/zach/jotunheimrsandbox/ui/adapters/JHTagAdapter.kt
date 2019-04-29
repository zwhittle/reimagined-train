package com.awesome.zach.jotunheimrsandbox.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.databinding.ListItemJhtagviewBinding

class JHTagAdapter(private val tags: ArrayList<Tag>, private val clickListener: View.OnClickListener?) :
    RecyclerView.Adapter<JHTagAdapter.JHTagViewHolder>() {

    companion object {
        const val LOG_TAG = "JHTagAdapter"
        const val TAG_TEXT = "tag-text"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JHTagViewHolder {
        val binding =
            DataBindingUtil.inflate<ListItemJhtagviewBinding>(LayoutInflater.from(parent.context), R.layout.list_item_jhtagview, parent, false)
        return JHTagViewHolder(binding)
    }

    override fun getItemCount() = tags.size

    fun getTags() = tags

    override fun onBindViewHolder(holder: JHTagViewHolder, position: Int) {
        holder.bindTag(tags[position], clickListener)
    }

    class JHTagViewHolder(private val binding: ListItemJhtagviewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTag(tag: Tag, listener: View.OnClickListener?) {
            itemView.background = null

            binding.apply {
                clickListener = listener
                tagText = tag.name
                // jhtagview.setColor(color)
                // jhtagview.setBackgroundColor(color)
            }
        }
    }
}