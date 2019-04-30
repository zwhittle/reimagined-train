package com.awesome.zach.jotunheimrsandbox.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.databinding.HolderTagBinding
import com.awesome.zach.jotunheimrsandbox.ui.viewholders.TagListViewHolder
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.TagListViewModel
import com.awesome.zach.jotunheimrsandbox.utils.Constants

class TagListAdapter(private val viewLifecycleOwner: LifecycleOwner, private val vm: TagListViewModel): ListAdapter<Tag, TagListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TagListViewHolder {
        val binding = HolderTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return TagListViewHolder(vm, binding)
    }

    override fun onBindViewHolder(holder: TagListViewHolder,
                                  position: Int) {
        val tag = getItem(position)

        if (tag != null) {
            holder.apply {
                val args = Bundle()
                args.putString(Constants.ARGUMENT_MODEL, Constants.MODEL_TAG)
                args.putLong(Constants.ARGUMENT_ITEM_ID, tag.id)
                args.putString(Constants.ARGUMENT_APP_TITLE, tag.name)

                bind(Navigation.createNavigateOnClickListener(R.id.taskListFragment, args), tag)
            }
        } else {
            holder.clear()
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Tag> = object : DiffUtil.ItemCallback<Tag>() {
            override fun areItemsTheSame(oldItem: Tag,
                                         newItem: Tag): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tag,
                                            newItem: Tag): Boolean {
                return oldItem == newItem
            }
        }
    }
}