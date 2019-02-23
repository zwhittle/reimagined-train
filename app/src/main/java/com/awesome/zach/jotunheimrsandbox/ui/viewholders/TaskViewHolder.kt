package com.awesome.zach.jotunheimrsandbox.ui.viewholders

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.databinding.ListItemTaskBinding

class TaskViewHolder(
    val binding: ListItemTaskBinding,
    private var selectedListener: OnTaskSelectedListener) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val SINGLE_SELECTION = 1
        const val MULTI_SELECTION = 2
    }

    lateinit var mTask: Task

    private val itemClickListener = View.OnClickListener {
        if (mTask.isSelected && itemViewType == MULTI_SELECTION) {
            setChecked(false)
        } else {
            setChecked(true)
        }

        selectedListener.onTaskSelected(mTask)
    }

    fun bind(item: Task) {
        binding.apply {
            clickListener = itemClickListener
            task = item
            executePendingBindings()
        }
    }

    fun setChecked(b: Boolean) {
        if (b) {
            binding.listItemTaskTopLayout.setBackgroundColor(Color.LTGRAY)
        } else {
            binding.listItemTaskTopLayout.background = null
        }

        mTask.isSelected = b
    }

    interface OnTaskSelectedListener {
        fun onTaskSelected(task: Task)
    }
}