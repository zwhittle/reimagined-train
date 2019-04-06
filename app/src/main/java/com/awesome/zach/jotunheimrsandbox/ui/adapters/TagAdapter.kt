package com.awesome.zach.jotunheimrsandbox.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.databinding.ListItemTagBinding
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ItemSelectedListener
import com.awesome.zach.jotunheimrsandbox.ui.viewholders.TagViewHolder

class TagAdapter(private val selectedListener: ItemSelectedListener,
                 private val isMultiSelectEnabled: Boolean) : RecyclerView.Adapter<TagViewHolder>(),
    ItemSelectedListener {

    private var mTags: List<Tag>? = null

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TagViewHolder {
        val binding =
            DataBindingUtil.inflate<ListItemTagBinding>(LayoutInflater.from(parent.context),
                                                        R.layout.list_item_tag,
                                                        parent,
                                                        false)
        return TagViewHolder(binding,
                             this)
    }

    override fun getItemCount() = mTags?.size ?: 0

    override fun onBindViewHolder(holder: TagViewHolder,
                                  position: Int) {
        val tag = mTags?.get(position)

        holder.apply {
            if (tag != null) {
                bind(tag)
                itemView.tag = tag
                setChecked(mTag.isSelected)
            }
        }
    }

    fun setTagsList(tags: List<Tag>) {
        if (mTags == null) {
            mTags = tags
            notifyItemRangeInserted(0,
                                    tags.size)
        } else {
            val result = DiffUtil.calculateDiff(TagDiffCallback(tags))
            mTags = tags
            result.dispatchUpdatesTo(this)
        }
    }

    fun getSelectedTags(): List<Tag> {
        val selectedTags = ArrayList<Tag>()

        mTags?.forEach {
            if (it.isSelected) selectedTags.add(it)
        }

        return selectedTags.toList()
    }

    fun clearSelectedTags() {
        mTags?.forEach {
            if (it.isSelected) it.isSelected = false
        }

        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (isMultiSelectEnabled) {
            TagViewHolder.MULTI_SELECTION
        } else {
            TagViewHolder.SINGLE_SELECTION
        }
    }

    override fun onItemSelected(item: Any) {
        if (item is Tag) {
            if (!isMultiSelectEnabled) {
                mTags?.forEach {
                    if (it != item && it.isSelected) {
                        it.isSelected = false
                    } else if (it == item && it.isSelected) {
                        it.isSelected = true
                    }
                }
                notifyDataSetChanged()
            }
            selectedListener.onItemSelected(item)
        }
    }

    inner class TagDiffCallback(private val tags: List<Tag>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int,
                                     newItemPosition: Int) = mTags?.get(oldItemPosition) == tags[newItemPosition]

        override fun getOldListSize() = mTags?.size ?: 0

        override fun getNewListSize() = tags.size

        override fun areContentsTheSame(oldItemPosition: Int,
                                        newItemPosition: Int): Boolean {
            val newTag = tags[newItemPosition]
            val oldTag = mTags?.get(oldItemPosition)

            return newTag.tagId == oldTag?.tagId
                && newTag.name == oldTag.name
                && newTag.colorId == oldTag.colorId
        }
    }
}