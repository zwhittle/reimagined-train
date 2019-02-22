package com.awesome.zach.jotunheimrsandbox.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awesome.zach.jotunheimrsandbox.data.repositories.TaskRepository
import com.awesome.zach.jotunheimrsandbox.data.repositories.TaskTagAssignmentRepository

class TaskListViewModelFactory(private val taskRepository: TaskRepository,
                               private val taskTagAssignmentRepository: TaskTagAssignmentRepository,
                               private val projectId: Long? = null,
                               private val tagId: Long? = null) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = TaskListViewModel(taskRepository,
                                                                                   taskTagAssignmentRepository,
                                                                                   projectId,
                                                                                   tagId) as T
}