package com.awesome.zach.jotunheimrsandbox.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awesome.zach.jotunheimrsandbox.data.repositories.TagRepository

class TagListViewModelFactory(private val repository: TagRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = TagListViewModel(repository) as T
}