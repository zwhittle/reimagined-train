package com.awesome.zach.jotunheimrsandbox.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awesome.zach.jotunheimrsandbox.data.repositories.ProjectRepository
import com.awesome.zach.jotunheimrsandbox.data.repositories.TagRepository
import com.awesome.zach.jotunheimrsandbox.data.repositories.TaskRepository
import com.awesome.zach.jotunheimrsandbox.data.repositories.TaskTagAssignmentRepository

class MainViewModelFactory(private val taskRepository: TaskRepository,
                           private val projectRepository: ProjectRepository,
                           private val tagRepository: TagRepository,
                           private val taskTagAssignmentRepository: TaskTagAssignmentRepository,
                           private val projectId: Long? = null,
                           private val taskId: Long? = null,
                           private val tagId: Long? = null) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = MainViewModel(
        taskRepository = taskRepository,
        projectRepository = projectRepository,
        tagRepository = tagRepository,
        taskTagAssignmentRepository = taskTagAssignmentRepository,
        projectId = projectId,
        taskId = taskId,
        tagId = tagId) as T
}