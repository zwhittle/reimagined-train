package com.awesome.zach.jotunheimrsandbox.ui.viewholders

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.databinding.ListItemTagBinding
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ItemSelectedListener

class TagViewHolder(val binding: ListItemTagBinding,
                    private var selectedListener: ItemSelectedListener) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val SINGLE_SELECTION = 1
        const val MULTI_SELECTION = 2
    }

    lateinit var mTag: Tag

    private val itemClickListener = View.OnClickListener {
        if (mTag.isSelected && itemViewType == MULTI_SELECTION) {
            setChecked(false)
        } else {
            setChecked(true)
        }

        selectedListener.onItemSelected(mTag)
    }

    fun bind(item: Tag) {
        binding.apply {
            clickListener = itemClickListener
            tag = item
            executePendingBindings()
        }
        mTag = item
    }

    fun setChecked(b: Boolean) {
        val checkedColor = Color.DKGRAY

        if (b) {
            binding.listItemTopLayout.setBackgroundColor(checkedColor)
            binding.tvListItemLabel.setBackgroundColor(checkedColor)
            binding.tvListItemId.setBackgroundColor(checkedColor)
            binding.ivListItemTagColor.setBackgroundColor(checkedColor)
        } else {
            binding.listItemTopLayout.background = null
            binding.tvListItemLabel.background = null
            binding.tvListItemId.background = null
            binding.ivListItemTagColor.background = null
        }

        mTag.isSelected = b
    }
}