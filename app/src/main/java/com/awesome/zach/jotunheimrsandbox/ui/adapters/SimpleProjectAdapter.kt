package com.awesome.zach.jotunheimrsandbox.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.Project
import com.awesome.zach.jotunheimrsandbox.databinding.ListItemProjectBinding
import com.awesome.zach.jotunheimrsandbox.utils.Utils

class SimpleProjectAdapter : RecyclerView.Adapter<SimpleProjectAdapter.SimpleProjectViewHolder>() {

    private var mProjects: List<Project>? = null

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SimpleProjectAdapter.SimpleProjectViewHolder {
        val binding =
            DataBindingUtil.inflate<ListItemProjectBinding>(LayoutInflater.from(parent.context),
                                                            R.layout.list_item_project,
                                                            parent,
                                                            false)
        return SimpleProjectViewHolder(binding)
    }

    override fun getItemCount() = mProjects?.size ?: 0

    override fun onBindViewHolder(holder: SimpleProjectAdapter.SimpleProjectViewHolder,
                                  position: Int) {
        val project = mProjects?.get(position)
        holder.apply {
            if (project != null) {
                bind(project)
                itemView.tag = project
            }
        }
    }

    fun setProjectsList(projects: List<Project>) {
        if (mProjects == null) {
            mProjects = projects
            notifyItemRangeInserted(0, projects.size)
        } else {
            val result = DiffUtil.calculateDiff(ProjectDiffCallback(projects))
            mProjects = projects
            result.dispatchUpdatesTo(this)
        }
    }

    inner class ProjectDiffCallback(private val projects: List<Project>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int,
                                     newItemPosition: Int) = mProjects?.get(oldItemPosition) == projects[newItemPosition]

        override fun getOldListSize() = mProjects?.size ?: 0

        override fun getNewListSize() = projects.size

        override fun areContentsTheSame(oldItemPosition: Int,
                                        newItemPosition: Int): Boolean {

            val newProject = projects[newItemPosition]
            val oldProject = mProjects?.get(oldItemPosition)

            return newProject.projectId == oldProject?.projectId
                && newProject.name == oldProject.name
                && newProject.colorId == oldProject.colorId
        }
    }

    class SimpleProjectViewHolder(private val binding: com.awesome.zach.jotunheimrsandbox.databinding.ListItemProjectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Project) {
            binding.apply {
                project = item
                val textColor = Color.parseColor(Utils.inverseHex(item.colorHex))
                binding.tvListItemId.setTextColor(textColor)
                binding.tvListItemLabel.setTextColor(textColor)
                executePendingBindings()
            }
        }
    }
}