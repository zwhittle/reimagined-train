package com.awesome.zach.jotunheimrsandbox.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.databinding.ListItemTaskBinding

class SimpleTaskAdapter : RecyclerView.Adapter<SimpleTaskAdapter.SimpleTaskViewHolder>() {

    private var mTasks: List<Task>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleTaskAdapter.SimpleTaskViewHolder {
        val binding = DataBindingUtil.inflate<ListItemTaskBinding>(
            LayoutInflater.from(parent.context), R.layout.list_item_task, parent, false
                                                                  )
        return SimpleTaskViewHolder(binding)
    }

    override fun getItemCount() = mTasks?.size ?: 0

    override fun onBindViewHolder(
        holder: SimpleTaskAdapter.SimpleTaskViewHolder, position: Int
                                 ) {
        val task = mTasks?.get(position)
        holder.apply {
            if (task != null) {
                // bind(Navigation.createNavigateOnClickListener(R.id.projectListFragment), task)
                bind(task)
                itemView.tag = task
            }
        }
    }

    fun setTasksList(tasks: List<Task>) {
        if (mTasks == null) {
            mTasks = tasks
            notifyItemRangeInserted(0, tasks.size)
        } else {
            val result = DiffUtil.calculateDiff(TaskDiffCallback(tasks))
            mTasks = tasks
            result.dispatchUpdatesTo(this)
        }
    }

    inner class TaskDiffCallback(private val tasks: List<Task>) : DiffUtil.Callback() {
        override fun areItemsTheSame(
            oldItemPosition: Int, newItemPosition: Int
                                    ) = mTasks?.get(oldItemPosition) == tasks[newItemPosition]

        override fun getOldListSize() = mTasks?.size ?: 0

        override fun getNewListSize() = tasks.size

        override fun areContentsTheSame(
            oldItemPosition: Int, newItemPosition: Int
                                       ): Boolean {

            val newTask = tasks[newItemPosition]
            val oldTask = mTasks?.get(oldItemPosition)

            return newTask.taskId == oldTask?.taskId && newTask.name == oldTask.name && newTask.date_start == oldTask.date_start && newTask.date_end == oldTask.date_end && newTask.completed == oldTask.completed && newTask.priority == oldTask.priority && newTask.projectId == oldTask.projectId
        }
    }

    class SimpleTaskViewHolder(private val binding: ListItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // fun bind(listener: View.OnClickListener, item: Task) {
        //     binding.apply {
        //         clickListener = listener
        //         task = item
        //         executePendingBindings()
        //     }
        // }

        fun bind(item: Task) {
            binding.apply {
                task = item
                executePendingBindings()
            }
        }
    }
}