package com.awesome.zach.jotunheimrsandbox.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awesome.zach.jotunheimrsandbox.data.repositories.TaskRepository

class TaskListViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = TaskListViewModel(repository) as T
}