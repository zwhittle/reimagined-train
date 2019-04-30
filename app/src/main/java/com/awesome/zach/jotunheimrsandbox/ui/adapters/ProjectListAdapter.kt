package com.awesome.zach.jotunheimrsandbox.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.awesome.zach.jotunheimrsandbox.R
import com.awesome.zach.jotunheimrsandbox.data.entities.Project
import com.awesome.zach.jotunheimrsandbox.databinding.HolderProjectBinding
import com.awesome.zach.jotunheimrsandbox.ui.viewholders.ProjectListViewHolder
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.ProjectListViewModel
import com.awesome.zach.jotunheimrsandbox.utils.Constants

class ProjectListAdapter(private val viewLifecycleOwner: LifecycleOwner, private val vm: ProjectListViewModel) : ListAdapter<Project, ProjectListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ProjectListViewHolder {
        val binding = HolderProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return ProjectListViewHolder(vm, binding)
    }

    override fun onBindViewHolder(holder: ProjectListViewHolder,
                                  position: Int) {
        val project = getItem(position)

        if (project != null) {
            holder.apply {
                val args = Bundle()
                args.putString(Constants.ARGUMENT_MODEL, Constants.MODEL_PROJECT)
                args.putLong(Constants.ARGUMENT_ITEM_ID, project.id)
                args.putString(Constants.ARGUMENT_APP_TITLE, project.name)

                bind(Navigation.createNavigateOnClickListener(R.id.taskListFragment, args), project)
            }
        } else {
            holder.clear()
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Project> = object : DiffUtil.ItemCallback<Project>() {
            override fun areItemsTheSame(oldItem: Project,
                                         newItem: Project): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Project,
                                            newItem: Project): Boolean {
                return oldItem == newItem
            }
        }
    }
}
