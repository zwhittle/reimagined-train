package com.awesome.zach.jotunheimrsandbox.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.databinding.HolderTaskBinding
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.TaskListViewModel
import org.koin.standalone.KoinComponent

class TaskListViewHolder(private val vm: TaskListViewModel, private val binding: HolderTaskBinding): RecyclerView.ViewHolder(binding.root),
    KoinComponent {

    fun bind(task: Task) {
        binding.task = task
        binding.vm = vm
        binding.executePendingBindings()
    }

    fun clear() {}
}