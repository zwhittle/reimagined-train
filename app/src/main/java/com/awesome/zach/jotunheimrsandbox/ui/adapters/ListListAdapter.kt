package com.awesome.zach.jotunheimrsandbox.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.awesome.zach.jotunheimrsandbox.data.entities.JHList
import com.awesome.zach.jotunheimrsandbox.databinding.HolderListBinding
import com.awesome.zach.jotunheimrsandbox.ui.viewholders.ListListViewHolder
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.ListListViewModel

class ListListAdapter(private val viewLifecycleOwner: LifecycleOwner, private val vm: ListListViewModel) : ListAdapter<JHList, ListListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ListListViewHolder {
        val binding = HolderListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return ListListViewHolder(vm, binding)
    }

    override fun onBindViewHolder(holder: ListListViewHolder,
                                  position: Int) {
        val list = getItem(position)

        if (list != null) {
            holder.bind(list)
        } else {
            holder.clear()
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<JHList> = object : DiffUtil.ItemCallback<JHList>() {
            override fun areItemsTheSame(oldItem: JHList,
                                         newItem: JHList): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: JHList,
                                            newItem: JHList): Boolean {
                return oldItem == newItem
            }
        }
    }
}