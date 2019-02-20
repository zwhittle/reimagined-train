package com.awesome.zach.jotunheimrsandbox.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awesome.zach.jotunheimrsandbox.data.repositories.ProjectRepository

class ProjectListViewModelFactory(private val repository: ProjectRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = ProjectListViewModel(repository) as T
}