package com.awesome.zach.jotunheimrsandbox.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.databinding.ListItemTaskBinding
import com.awesome.zach.jotunheimrsandbox.ui.callbacks.ItemSelectedListener
import com.awesome.zach.jotunheimrsandbox.ui.viewholders.TaskViewHolder

class TaskAdapter(
    private val selectedListener: ItemSelectedListener,
    private val isMultiSelectEnabled: Boolean
                 ) : RecyclerView.Adapter<TaskViewHolder>(), ItemSelectedListener {

    private var mTasks: List<Task>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = DataBindingUtil.inflate<ListItemTaskBinding>(
            LayoutInflater.from(parent.context), R.layout.list_item_task, parent, false
                                                                  )
        return TaskViewHolder(binding, this)
    }

    override fun getItemCount() = mTasks?.size ?: 0

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = mTasks?.get(position)

        holder.apply {
            if (task != null) {
                bind(task)
                itemView.tag = task
                setChecked(mTask.isSelected)
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

    fun getSelectedTasks(): List<Task> {
        val selectedTasks = ArrayList<Task>()

        mTasks?.forEach {
            if (it.isSelected) selectedTasks.add(it)
        }

        return selectedTasks.toList()
    }

    fun clearSelectedTasks() {
        mTasks?.forEach {
            if (it.isSelected) it.isSelected = false
        }

        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (isMultiSelectEnabled) {
            TaskViewHolder.MULTI_SELECTION
        } else {
            TaskViewHolder.SINGLE_SELECTION
        }
    }

    override fun onItemSelected(item: Any) {
        if (item is Task) {
            if (!isMultiSelectEnabled) {
                mTasks?.forEach {
                    if (it != item && it.isSelected) {
                        it.isSelected = false
                    } else if (it == item && it.isSelected) {
                        it.isSelected = true
                    }
                }
                notifyDataSetChanged()
            }
            selectedListener.onItemSelected(item)
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
}