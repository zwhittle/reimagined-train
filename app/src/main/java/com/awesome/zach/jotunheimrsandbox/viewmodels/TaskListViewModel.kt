package com.awesome.zach.jotunheimrsandbox.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.data.repositories.TaskRepository
import com.awesome.zach.jotunheimrsandbox.data.repositories.TaskTagAssignmentRepository
import java.lang.Exception
import java.lang.IllegalArgumentException

class TaskListViewModel internal constructor(taskRepository: TaskRepository,
                                             taskTagAssignmentRepository: TaskTagAssignmentRepository,
                                             projectId: Long? = null,
                                             tagId: Long? = null) : ViewModel() {

    private val tasksList = MediatorLiveData<List<Task>>()

    companion object {
        const val LOG_TAG = "TaskListViewModel"
    }

    init {
        val liveTasksList = taskRepository.getAllTasksLive()
        tasksList.addSource(liveTasksList, tasksList::setValue)

        // if (projectId == null) {
        //     // no projectId was passed
        //     if (tagId == null) {
        //         // both are null, return all tasks
        //         val liveTasksList = taskRepository.getAllTasksLive()
        //         tasksList.addSource(liveTasksList, tasksList::setValue)
        //     } else {
        //         // projectId is null, but a tagId was passed, return tasks for that tagId
        //         val liveTasksList = taskTagAssignmentRepository.getTasksWithTagLive(tagId)
        //         tasksList.addSource(liveTasksList, tasksList::setValue)
        //     }
        // } else {
        //     // projectId was passed
        //     if (tagId == null) {
        //         // tagId is null, return tasks for the projectId
        //         val liveTasksList = taskRepository.getTasksForProjectLive(projectId)
        //         tasksList.addSource(liveTasksList, tasksList::setValue)
        //     } else {
        //         // both were passed, return tasks for the projectID but filter for the tags first
        //         // TODO: implement this, for now throw an exception
        //         throw IllegalArgumentException("values found for both tagId and projectId. only pass one of them, not both")
        //     }
        // }
    }

    fun getTasks() = tasksList
}