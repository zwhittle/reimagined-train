package com.awesome.zach.jotunheimrsandbox.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.JHList
import com.awesome.zach.jotunheimrsandbox.databinding.HolderListBinding
import com.awesome.zach.jotunheimrsandbox.ui.viewholders.ListListViewHolder
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.ListListViewModel
import com.awesome.zach.jotunheimrsandbox.utils.Constants

class ListListAdapter(private val viewLifecycleOwner: LifecycleOwner,
                      private val vm: ListListViewModel) : ListAdapter<JHList, ListListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ListListViewHolder {
        val binding = HolderListBinding.inflate(LayoutInflater.from(parent.context),
                                                parent,
                                                false)
        binding.lifecycleOwner = viewLifecycleOwner
        return ListListViewHolder(vm,
                                  binding)
    }

    override fun onBindViewHolder(holder: ListListViewHolder,
                                  position: Int) {
        val list = getItem(position)


        if (list != null) {
            holder.apply {
                val args = Bundle()
                args.putString(Constants.ARGUMENT_MODEL,
                               Constants.MODEL_LIST)
                args.putLong(Constants.ARGUMENT_ITEM_ID,
                             list.id)
                args.putString(Constants.ARGUMENT_APP_TITLE,
                               list.name)

                bind(Navigation.createNavigateOnClickListener(R.id.taskListFragment,
                                                              args), list)
            }
        } else {
            holder.clear()
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<JHList> =
            object : DiffUtil.ItemCallback<JHList>() {
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