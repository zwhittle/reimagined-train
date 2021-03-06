package com.awesome.zach.jotunheimrsandbox.ui.adapters

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.databinding.ListItemTagBinding
import com.awesome.zach.jotunheimrsandbox.utils.Constants
import com.awesome.zach.jotunheimrsandbox.utils.Utils

class SimpleTagAdapter : RecyclerView.Adapter<SimpleTagAdapter.SimpleTagViewHolder>() {

    private var mTags: List<Tag>? = null

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SimpleTagViewHolder {
        val binding =
            DataBindingUtil.inflate<ListItemTagBinding>(LayoutInflater.from(parent.context),
                                                        R.layout.list_item_tag,
                                                        parent,
                                                        false)
        return SimpleTagViewHolder(binding)
    }

    override fun getItemCount() = mTags?.size ?: 0

    override fun onBindViewHolder(holder: SimpleTagViewHolder,
                                  position: Int) {
        val tag = mTags?.get(position)
        holder.apply {
            if (tag != null) {
                val args = Bundle()
                args.putLong(Constants.ARGUMENT_TAG_ID, tag.tagId)
                args.putString(Constants.ARGUMENT_TAG_NAME, tag.name)
                bind(Navigation.createNavigateOnClickListener(R.id.taskListFragment, args), tag)
                itemView.tag = tag
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

    class SimpleTagViewHolder(private val binding: ListItemTagBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: Tag) {
            binding.apply {
                clickListener = listener
                tag = item
                val textColor = Color.parseColor(Utils.inverseHex(item.colorHex))
                // binding.tvListItemId.setTextColor(textColor)
                // binding.tvListItemLabel.setTextColor(textColor)
                executePendingBindings()
            }
        }
    }
}