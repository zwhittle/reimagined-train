package com.awesome.zach.jotunheimrsandbox.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.databinding.HolderTagBinding
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.TagListViewModel
import org.koin.standalone.KoinComponent

class TagListViewHolder(private val vm: TagListViewModel,
                        private val binding: HolderTagBinding) : RecyclerView.ViewHolder(binding.root),
    KoinComponent {

    fun bind(listener: View.OnClickListener, tag: Tag) {
        binding.apply {
            this.clickListener = listener
            this.tag = tag
            this.vm = vm

            executePendingBindings()
        }
    }

    fun clear() {}
}