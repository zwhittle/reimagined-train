package com.awesome.zach.jotunheimrsandbox.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awesome.zach.jotunheimrsandbox.data.entities.Project
import com.awesome.zach.jotunheimrsandbox.databinding.HolderProjectBinding
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.ProjectListViewModel
import org.koin.standalone.KoinComponent

class ProjectListViewHolder(private val vm: ProjectListViewModel, private val binding: HolderProjectBinding): RecyclerView.ViewHolder(binding.root), KoinComponent {

    fun bind(listener: View.OnClickListener, project: Project) {
        binding.apply {
            this.clickListener = listener
            this.project = project
            this.vm = vm

            executePendingBindings()
        }
    }

    fun clear() {}
}