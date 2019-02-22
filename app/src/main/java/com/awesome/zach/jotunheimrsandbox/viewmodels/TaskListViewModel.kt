package com.awesome.zach.jotunheimrsandbox.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.data.repositories.TaskRepository

class TaskListViewModel internal constructor(private val taskRepository: TaskRepository) :
    ViewModel() {

    private val tasksList = MediatorLiveData<List<Task>>()

    companion object {
        const val LOG_TAG = "TaskListViewModel"
    }

    init {
        val liveTasksList = taskRepository.getAllTasksLive()
        tasksList.addSource(liveTasksList, tasksList::setValue)
    }

    fun getTasks() = tasksList

}