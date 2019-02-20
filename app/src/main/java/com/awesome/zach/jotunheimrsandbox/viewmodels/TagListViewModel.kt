package com.awesome.zach.jotunheimrsandbox.viewmodels

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.data.repositories.ProjectRepository
import com.awesome.zach.jotunheimrsandbox.data.repositories.TagRepository
import com.awesome.zach.jotunheimrsandbox.data.repositories.TaskRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TagListViewModel internal constructor(private val tagRepository: TagRepository) :
    ViewModel() {

    private val tagsList = MediatorLiveData<List<Tag>>()

    companion object {
        const val LOG_TAG = "TagListViewModel"
    }

    init {
        val liveTagsList = tagRepository.getAllTagsLive()
        tagsList.addSource(
            liveTagsList,
            tagsList::setValue)
    }

    fun getTags() = tagsList
}