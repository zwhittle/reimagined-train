package com.awesome.zach.jotunheimrsandbox.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.databinding.HolderTaskBinding
import com.awesome.zach.jotunheimrsandbox.ui.viewholders.TaskListViewHolder
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.TaskListViewModel

class TaskListAdapter(private val viewLifecycleOwner: LifecycleOwner, private val vm: TaskListViewModel): ListAdapter<Task, TaskListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TaskListViewHolder {
        val binding = HolderTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return TaskListViewHolder(vm, binding)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder,
                                  position: Int) {
        val task = getItem(position)

        if (task != null) {
            holder.bind(task)
        } else {
            holder.clear()
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Task> = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task,
                                         newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task,
                                            newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }
}