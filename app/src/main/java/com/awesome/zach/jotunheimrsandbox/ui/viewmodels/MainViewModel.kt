package com.awesome.zach.jotunheimrsandbox.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.zach.jotunheimrsandbox.data.entities.*
import com.awesome.zach.jotunheimrsandbox.data.repositories.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel internal constructor(val colorRepository: ColorRepository,
                                         val taskRepository: TaskRepository,
                                         val projectRepository: ProjectRepository,
                                         val tagRepository: TagRepository,
                                         val taskTagAssignmentRepository: TaskTagAssignmentRepository,
                                         projectId: Long? = null, tagId: Long? = null,
                                         taskId: Long? = null) : ViewModel() {
    companion object {
        const val LOG_TAG = "MainViewModel"
    }

    private val tasksList = MediatorLiveData<List<Task>>()
    private val projectsList = MediatorLiveData<List<Project>>()
    private val tagsList = MediatorLiveData<List<Tag>>()
    private val colorsList = MediatorLiveData<List<Color>>()
    // private var colorsList = listOf<Color>()

    private val selectedTags = MutableLiveData<List<Tag>>()

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()

    /**
     * This is the scope for all coroutines launched by [PlantDetailViewModel].
     *
     * Since we pass [viewModelJob], you can cancel all coroutines launched by [viewModelScope] by calling
     * viewModelJob.cancel().  This is called in [onCleared].
     */
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        setupTasksList(taskRepository, taskTagAssignmentRepository, projectId, tagId)
        setupProjectsList(projectRepository)
        setupTagsList(tagRepository)
        setupColorsList(colorRepository)
    }

    private fun setupColorsList(colorRepository: ColorRepository) {
        val liveColorsList = colorRepository.getAllColorsLive()
        colorsList.addSource(liveColorsList, colorsList::setValue)
        // viewModelScope.launch {
        //     colorsList = colorRepository.getAllColors()
        // }
    }

    private fun setupTagsList(tagRepository: TagRepository) {
        val liveTagsList = tagRepository.getAllTagsLive()
        tagsList.addSource(liveTagsList, tagsList::setValue)
    }

    private fun setupProjectsList(projectRepository: ProjectRepository) {
        val liveProjectsList = projectRepository.getAllProjectsLive()
        projectsList.addSource(liveProjectsList, projectsList::setValue)
    }

    private fun setupTasksList(taskRepository: TaskRepository,
                               taskTagAssignmentRepository: TaskTagAssignmentRepository,
                               projectId: Long?, tagId: Long?) {

        if (projectId == null) {
            // no projectId was passed
            if (tagId == null) {
                // both are null, return inbox tasks
                 val liveTasksList = taskRepository.getActiveTasksLive()
//                val liveTasksList = taskRepository.getInboxTasksLive()
                tasksList.addSource(liveTasksList, tasksList::setValue)
            } else {
                // projectId is null, but a tagId was passed, return tasks for that tagId
                val liveTasksList = taskTagAssignmentRepository.getActiveTasksWithTagLive(tagId)
                tasksList.addSource(liveTasksList, tasksList::setValue)
            }
        } else {
            // projectId was passed
            if (tagId == null) {
                // tagId is null, return tasks for the projectId
                if (projectId > 0) {
                    val liveTasksList = taskRepository.getTasksForProjectLive(projectId)
                    tasksList.addSource(liveTasksList, tasksList::setValue)
                } else if (projectId == 0L) {
                    val liveTasksList = taskRepository.getInboxTasksLive()
                    tasksList.addSource(liveTasksList, tasksList::setValue)
                }
            } else {
                // both were passed, return tasks for the projectID but filter for the tags first
                // TODO: implement this, for now throw an exception
                throw IllegalArgumentException(
                    "values found for both tagId and projectId. only pass one of them, not both")
            }
        }
    }

    fun getTasks() = tasksList

    fun getProjects() = projectsList

    fun getTags() = tagsList

    fun getColors() = colorsList

    fun getSelectedTags(): MutableLiveData<List<Tag>> {
        return selectedTags
    }

    fun addTaskToDb(name: String, project: Project? = null, tags: List<Tag>? = null) {
        viewModelScope.launch {

            val pid = project?.projectId

            val task = Task(name = name, projectId = pid)
            task.taskId = taskRepository.insertTask(task)


            tags?.forEach {
                addTaskTagAssignmentToDb(taskId = task.taskId, tagId = it.tagId)
            }
        }
    }

    private fun addTaskTagAssignmentToDb(taskId: Long, tagId: Long) {
        viewModelScope.launch {
            taskTagAssignmentRepository.insertTaskTagAssignment(TaskTagAssignment(taskId = taskId, tagId = tagId))
        }
    }

    fun addProjectToDb(name: String, colorId: Long? = null) {
        viewModelScope.launch {
            val cid = colorId ?: colorRepository.getAllColors()[0].colorId

            val project = Project(name = name, colorId = cid)
            projectRepository.insertProject(project)
        }
    }

    fun addTagToDb(name: String, colorId: Long? = null) {
        viewModelScope.launch {
            val cid = colorId ?: colorRepository.getAllColors()[0].colorId

            val tag = Tag(name = name, colorId = cid)
            tagRepository.insertTag(tag)
        }
    }

    fun updateTasks(tasks: List<Task>) {
        viewModelScope.launch {
            val count = taskRepository.bulkUpdateTasks(tasks)
            Log.d(LOG_TAG, "$count tasks updated")
        }
    }

    fun deleteTasks(tasks: List<Task>) {
        viewModelScope.launch {
            val count = taskRepository.bulkDeleteTasks(tasks)
            Log.d(LOG_TAG, "$count tasks deleted")
        }
    }

    fun updateSelectedTags(tags: List<Tag>) {
        selectedTags.value = tags
    }


}