package com.awesome.zach.jotunheimrsandbox.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.data.entities.JHList
import com.awesome.zach.jotunheimrsandbox.databinding.HolderListBinding
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.ListListViewModel
import org.koin.standalone.KoinComponent

class ListListViewHolder(private val vm: ListListViewModel, private val binding: HolderListBinding) :
RecyclerView.ViewHolder(binding.root), KoinComponent {

    fun bind(list: JHList) {
        binding.list = list
        binding.vm = vm
        binding.executePendingBindings()
    }

    fun clear() {}
}