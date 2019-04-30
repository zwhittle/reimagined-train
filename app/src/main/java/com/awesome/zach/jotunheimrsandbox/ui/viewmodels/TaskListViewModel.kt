package com.awesome.zach.jotunheimrsandbox.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.data.repositories.TaskRepository
import kotlinx.coroutines.*

class TaskListViewModel(private val taskRepository: TaskRepository): ViewModel() {

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()

    /**
     * This is the scope for all coroutines launched by [MainViewModel].
     *
     * Since we pass [viewModelJob], you can cancel all coroutines launched by [viewModelScope] by calling
     * viewModelJob.cancel().  This is called in [onCleared].
     */
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun tasks() = taskRepository.tasks()

    fun tasksByProject(projectId: Long) = taskRepository.tasksByProject(projectId)

    fun tasksByList(listId: Long) = taskRepository.tasksByList(listId)

    fun tasksByTag (tagId: Long) = taskRepository.tasksByTag(tagId)

    fun insert(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = newAsync(name)
        }
    }

    suspend fun newAsync(name: String): Deferred<Task> {
        return taskRepository.newAsync(name)
    }

    suspend fun delete(task: Task) {
        taskRepository.delete(task)
    }

    suspend fun delete(taskId: Long) {
        taskRepository.delete(taskId)
    }

}