package com.awesome.zach.jotunheimrsandbox.ui.viewholders

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.databinding.ListItemTaskBinding
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ItemSelectedListener

class TaskViewHolder(
    val binding: ListItemTaskBinding,
    private var selectedListener: ItemSelectedListener
                    ) : RecyclerView.ViewHolder(binding.root) {

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

        selectedListener.onItemSelected(mTask)
    }

    fun bind(item: Task) {
        binding.apply {
            clickListener = itemClickListener
            task = item
            executePendingBindings()
        }
        mTask = item
    }

    fun setChecked(b: Boolean) {
        val checkedColor = Color.DKGRAY

        if (b) {
            binding.listItemTaskTopLayout.setBackgroundColor(checkedColor)
            binding.listItemTaskRow1.setBackgroundColor(checkedColor)
            binding.listItemTaskRow2.setBackgroundColor(checkedColor)
            binding.tvListItemTaskId.setBackgroundColor(checkedColor)
            binding.tvListItemTaskLabel.setBackgroundColor(checkedColor)
            binding.tvListItemTaskProject.setBackgroundColor(checkedColor)
        } else {
            binding.listItemTaskTopLayout.background = null
            binding.listItemTaskRow1.background = null
            binding.listItemTaskRow2.background = null
            binding.tvListItemTaskId.background = null
            binding.tvListItemTaskLabel.background = null
            binding.tvListItemTaskProject.background = null
        }

        mTask.isSelected = b
    }


}