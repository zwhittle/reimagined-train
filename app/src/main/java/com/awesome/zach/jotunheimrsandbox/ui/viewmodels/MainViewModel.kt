package com.awesome.zach.jotunheimrsandbox.ui.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awesome.zach.jotunheimrsandbox.data.entities.*
import com.awesome.zach.jotunheimrsandbox.data.repositories.*
import com.awesome.zach.jotunheimrsandbox.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel internal constructor(val colorRepository: ColorRepository,
                                         val taskRepository: TaskRepository,
                                         val projectRepository: ProjectRepository,
                                         val tagRepository: TagRepository,
                                         val listRepository: ListRepository,
                                         val taskTagAssignmentRepository: TaskTagAssignmentRepository,
                                         var projectId: Long? = null,
                                         var tagId: Long? = null,
                                         var taskId: Long? = null,
                                         var listId: Long? = null) : ViewModel() {
    companion object {
        const val LOG_TAG = "MainViewModel"
    }

    //    private val tasksList = MediatorLiveData<List<Task>>()
    //    private val projectsList = MediatorLiveData<List<Project>>()
    //    private val tagsList = MediatorLiveData<List<Tag>>()
    //    private val colorsList = MediatorLiveData<List<Color>>()
    //    private val listsList = MediatorLiveData<List<JHList>>()
    //    private val tasksList = MutableLiveData<List<Task>>()
    //    private val projectsList = MutableLiveData<List<Project>>()
    //    private val tagsList = MutableLiveData<List<Tag>>()
    //    private val colorsList = MutableLiveData<List<Color>>()
    //    private val listsList = MutableLiveData<List<JHList>>()

    private lateinit var tasksList: LiveData<List<Task>>
    private lateinit var projectsList: LiveData<List<Project>>
    private lateinit var tagsList: LiveData<List<Tag>>
    private lateinit var colorsList: LiveData<List<Color>>
    private lateinit var listsList: LiveData<List<JHList>>

    private val selectedTags = MutableLiveData<List<Tag>>()
    private val selectedList = MutableLiveData<JHList>()

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
        setupColorsList(colorRepository)
        setupListsList(listRepository)
        setupTagsList(tagRepository)
        setupProjectsList(projectRepository)
        setupTasksList(taskRepository = taskRepository,
                       taskTagAssignmentRepository = taskTagAssignmentRepository,
                       projectId = projectId,
                       tagId = tagId,
                       listId = listId)
    }

    private fun setupColorsList(colorRepository: ColorRepository) {
        val liveColorsList = colorRepository.getAllColorsLive()
        colorsList = liveColorsList

        //        colorsList.addSource(liveColorsList,
        //                             colorsList::setValue)
        // viewModelScope.launch {
        //     colorsList = colorRepository.getAllColors()
        // }
    }

    private fun setupListsList(listRepository: ListRepository) {
        val liveListsList = listRepository.getAllListsLive()
        listsList = liveListsList

        //        listsList.addSource(liveListsList,
        //                            listsList::setValue)
    }

    private fun setupTagsList(tagRepository: TagRepository) {
        val liveTagsList = tagRepository.getAllTagsLive()
        tagsList = liveTagsList

        //        tagsList.addSource(liveTagsList,
        //                           tagsList::setValue)
    }

    private fun setupProjectsList(projectRepository: ProjectRepository) {
        val liveProjectsList = projectRepository.getAllProjectsLive()
        projectsList = liveProjectsList

        //        projectsList.addSource(liveProjectsList,
        //                               projectsList::setValue)
    }

    private fun setupTasksList(taskRepository: TaskRepository,
                               taskTagAssignmentRepository: TaskTagAssignmentRepository,
                               projectId: Long? = null,
                               tagId: Long? = null,
                               listId: Long? = null) {

        val projectIdPassed = projectId != null
        val tagIdPassed = tagId != null
        val listIdPassed = listId != null

        when {
            projectIdPassed -> {
                if (projectId!! > 0) {
                    val liveTasksList = taskRepository.getTasksForProjectLive(projectId)
                    tasksList = liveTasksList
                    //                    tasksList.addSource(liveTasksList,
                    //                                        tasksList::setValue)
                } else if (projectId == 0L) {
                    val liveTasksList = taskRepository.getInboxTasksLive()
                    tasksList = liveTasksList
                    //                    tasksList.addSource(liveTasksList,
                    //                                        tasksList::setValue)
                }
            }
            tagIdPassed     -> {
                if (tagId!! > 0) {
                    val liveTasksList = taskTagAssignmentRepository.getTasksWithTagLive(tagId)
                    tasksList = liveTasksList
                    //                    tasksList.addSource(liveTasksList,
                    //                        tasksList::setValue)
                } else if (tagId == 0L) {
                    val liveTasksList = taskRepository.getInboxTasksLive()
                    tasksList = liveTasksList
                    //                    tasksList.addSource(liveTasksList,
                    //                        tasksList::setValue)
                }
            }
            listIdPassed    -> {
                if (listId!! > 0) {
                    val liveTasksList = taskRepository.getTasksOnListLive(listId)
                    tasksList = liveTasksList
                    //                    tasksList.addSource(liveTasksList,
                    //                        tasksList::setValue)
                } else if (listId == 0L) {
                    val liveTasksList = taskRepository.getInboxTasksLive()
                    tasksList = liveTasksList
                    //                    tasksList.addSource(liveTasksList,
                    //                        tasksList::setValue)
                }
            }
            else            -> {
                val liveTasksList = taskRepository.getActiveTasksLive()
                tasksList = liveTasksList
                //                tasksList.addSource(liveTasksList,
                //                                    tasksList::setValue)
            }
        }
    }

    fun getTasks(args: Bundle? = null): LiveData<List<Task>> {
        if (args != null) {
            when {
                args.containsKey(Constants.ARGUMENT_LIST_ID)    -> {
                    val listId = args.getLong(Constants.ARGUMENT_LIST_ID)
                    return getTasksForList(listId)
                }
                args.containsKey(Constants.ARGUMENT_PROJECT_ID) -> {
                    val projectId = args.getLong(Constants.ARGUMENT_PROJECT_ID)
                    return getTasksForProject(projectId)
                }
                args.containsKey(Constants.ARGUMENT_TAG_ID)     -> {
                    val tagId = args.getLong(Constants.ARGUMENT_TAG_ID)
                    return getTasksForTag(tagId)
                }
                else                                            -> {
                    return getAllTasks()
                }
            }
        }

        return tasksList
    }

    private fun getAllTasks(): LiveData<List<Task>> {
        setupTasksList(taskRepository = taskRepository,
            taskTagAssignmentRepository = taskTagAssignmentRepository)

        return tasksList
    }

    private fun getTasksForList(listId: Long): LiveData<List<Task>> {
        setupTasksList(taskRepository = taskRepository,
                       taskTagAssignmentRepository = taskTagAssignmentRepository,
                       listId = listId)
        return tasksList
    }

    private fun getTasksForProject(projectId: Long): LiveData<List<Task>> {
        setupTasksList(taskRepository = taskRepository,
                       taskTagAssignmentRepository = taskTagAssignmentRepository,
                       projectId = projectId)
        return tasksList
    }

    private fun getTasksForTag(tagId: Long): LiveData<List<Task>> {
        setupTasksList(taskRepository = taskRepository,
                       taskTagAssignmentRepository = taskTagAssignmentRepository,
                       tagId = tagId)
        return tasksList
    }

    fun getProjects() = projectsList

    fun getTags() = tagsList

    fun getColors() = colorsList

    fun getLists() = listsList

    fun setListId(listId: Long) {
        setupTasksList(taskRepository = taskRepository,
                       taskTagAssignmentRepository = taskTagAssignmentRepository,
                       listId = listId)
    }

    fun setTagId(tagId: Long) {
    }

    fun setProjectId(projectId: Long) {
        setupTasksList(taskRepository = taskRepository,
                       taskTagAssignmentRepository = taskTagAssignmentRepository,
                       projectId = projectId)
    }

    fun getSelectedTags(): MutableLiveData<List<Tag>> {
        return selectedTags
    }

    fun addTaskToDb(name: String,
                    project: Project? = null,
                    list: JHList? = null,
                    tags: List<Tag>? = null) {
        viewModelScope.launch {

            val pid = project?.projectId
            val lid = list?.listId

            val task = Task(taskName = name,
                            projectId = pid,
                            listId = lid)
            task.taskId = taskRepository.insertTask(task)

            Log.d(LOG_TAG, "$task added to DB")

            tags?.forEach {
                addTaskTagAssignmentToDb(taskId = task.taskId,
                                         tagId = it.tagId)
            }
        }
    }

    private fun addTaskTagAssignmentToDb(taskId: Long,
                                         tagId: Long) {
        viewModelScope.launch {
            taskTagAssignmentRepository.insertTaskTagAssignment(TaskTagAssignment(taskId = taskId,
                                                                                  tagId = tagId))
        }
    }

    fun addProjectToDb(name: String,
                       colorId: Long? = null) {
        viewModelScope.launch {
            val cid = colorId ?: colorRepository.getAllColors()[0].colorId

            val project = Project(name = name,
                                  colorId = cid)
            projectRepository.insertProject(project)
        }
    }

    fun addTagToDb(name: String,
                   colorId: Long? = null) {
        viewModelScope.launch {
            val cid = colorId ?: colorRepository.getAllColors()[0].colorId

            val tag = Tag(name = name,
                          colorId = cid)
            tagRepository.insertTag(tag)
        }
    }

    fun updateTasks(tasks: List<Task>) {
        viewModelScope.launch {
            val count = taskRepository.bulkUpdateTasks(tasks)
            Log.d(LOG_TAG,
                  "$count tasks updated")
        }
    }

    fun deleteTasks(tasks: List<Task>) {
        viewModelScope.launch {
            val count = taskRepository.bulkDeleteTasks(tasks)
            Log.d(LOG_TAG,
                  "$count tasks deleted")
        }
    }

    fun updateSelectedTags(tags: List<Tag>) {
        selectedTags.value = tags
    }

    fun clearSelectedTags() {
        selectedTags.value = null
    }

    fun updateSelectedList(list: JHList) {
        selectedList.value = list
    }
}