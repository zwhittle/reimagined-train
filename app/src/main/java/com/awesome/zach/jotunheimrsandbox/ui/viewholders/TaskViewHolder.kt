package com.awesome.zach.jotunheimrsandbox.ui.viewholders

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.databinding.ListItemTaskBinding
import com.awesome.zach.jotunheimrsandbox.ui.adapters.JHTagAdapter
import com.awesome.zach.jotunheimrsandbox.ui.listeners.ItemSelectedListener
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.MainViewModel

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

    fun bind(item: Task, viewModel: MainViewModel? = null) {

        if (viewModel != null) {
            val tags = viewModel.getTagsForTask(item.id)
            val adapter = JHTagAdapter(tags = tags,
                                       clickListener = null)
            val linearLayoutManager = LinearLayoutManager(binding.root.context,
                                                          LinearLayout.HORIZONTAL,
                                                          false)

            binding.apply {
                rvListItemTaskTags.adapter = adapter
                rvListItemTaskTags.layoutManager = linearLayoutManager
            }
        }

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
            binding.rvListItemTaskTags.setBackgroundColor(checkedColor)
            binding.listItemTaskTagsArea.setBackgroundColor(checkedColor)
            binding.tvListItemTaskList.setBackgroundColor(checkedColor)
            binding.tvListItemTaskName.setBackgroundColor(checkedColor)
            binding.tvListItemTaskProject.setBackgroundColor(checkedColor)
            binding.tvListItemTaskDue.setBackgroundColor(checkedColor)
        } else {
            binding.listItemTaskTopLayout.background = null
            binding.listItemTaskRow1.background = null
            binding.listItemTaskRow2.background = null
            binding.rvListItemTaskTags.setBackgroundColor(0)
            binding.listItemTaskTagsArea.background = null
            binding.tvListItemTaskList.background = null
            binding.tvListItemTaskName.background = null
            binding.tvListItemTaskProject.background = null
            binding.tvListItemTaskDue.background = null
        }

        mTask.isSelected = b
    }
}