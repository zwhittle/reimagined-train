package com.awesome.zach.jotunheimrsandbox.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.zach.jotunheimrsandbox.data.entities.JHList
import com.awesome.zach.jotunheimrsandbox.data.entities.Project
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.data.repositories.TaskRepository
import com.awesome.zach.jotunheimrsandbox.utils.LogUtils
import kotlinx.coroutines.*

class TaskListViewModel(private val taskRepository: TaskRepository): ViewModel() {

    // used in the new task fragment
    private var selectedTags = MutableLiveData<List<Tag>>()

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

    fun onAddTaskClicked() {

    }

    // used in the new task fragment
    fun getSelectedTags() = selectedTags

    // used in the new task fragment
    fun updateSelectedTags(tags: List<Tag>) {
        selectedTags.value = tags
    }

    // used in the new task fragment
    fun clearSelectedTags() {
        selectedTags.value = null
    }

    fun tasksByProject(projectId: Long) = taskRepository.tasksByProject(projectId)

    fun tasksByList(listId: Long) = taskRepository.tasksByList(listId)

    fun tasksByTag (tagId: Long) = taskRepository.tasksByTag(tagId)

    fun insert(name: String, project: Project? = null, list: JHList? = null, tags: ArrayList<Tag>? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = newAsync(name, project, list, tags)
            LogUtils.log(LOG_TAG, "D::Task:$task")
        }
    }

    suspend fun newAsync(name: String, project: Project? = null, list: JHList? = null, tags: ArrayList<Tag>? = null): Deferred<Task> {
        val task = Task(name = name, projectId = project?.id, listId = list?.id)
        tags?.forEach {
            val taskTag = taskRepository.newTaskTagAsync(taskId = task.id, tagId = it.id)
            LogUtils.log(LOG_TAG, "D::Task:$taskTag")
        }

        return taskRepository.newAsync(task)
    }

    suspend fun delete(task: Task) {
        taskRepository.delete(task)
    }

    suspend fun delete(taskId: Long) {
        taskRepository.delete(taskId)
    }

    companion object {
        const val LOG_TAG = "TaskListViewModel"
    }
}