package com.awesome.zach.jotunheimrsandbox.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.data.entities.JHList
import com.awesome.zach.jotunheimrsandbox.databinding.HolderListBinding
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.ListListViewModel
import org.koin.standalone.KoinComponent

class ListListViewHolder(private val vm: ListListViewModel, private val binding: HolderListBinding) :
RecyclerView.ViewHolder(binding.root), KoinComponent {

    fun bind(listener: View.OnClickListener, list: JHList) {
        binding.apply {
            this.clickListener = listener
            this.list = list
            this.vm = vm

            executePendingBindings()
        }
    }

    fun clear() {}
}