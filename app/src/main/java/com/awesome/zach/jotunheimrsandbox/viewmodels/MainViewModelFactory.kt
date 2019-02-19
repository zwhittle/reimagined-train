package com.awesome.zach.jotunheimrsandbox.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awesome.zach.jotunheimrsandbox.db.repositories.ProjectRepository
import com.awesome.zach.jotunheimrsandbox.db.repositories.TagRepository
import com.awesome.zach.jotunheimrsandbox.db.repositories.TaskRepository

class MainViewModelFactory(
    val tagRepository: TagRepository,
    val projectRepository: ProjectRepository,
    val taskRepository: TaskRepository
                          ) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            tagRepository = tagRepository,
            projectRepository = projectRepository,
            taskRepository = taskRepository) as T
    }
}