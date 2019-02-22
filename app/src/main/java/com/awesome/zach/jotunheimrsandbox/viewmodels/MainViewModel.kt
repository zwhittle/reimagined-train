package com.awesome.zach.jotunheimrsandbox.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.awesome.zach.jotunheimrsandbox.data.entities.Project
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.data.entities.Task
import com.awesome.zach.jotunheimrsandbox.data.repositories.ProjectRepository
import com.awesome.zach.jotunheimrsandbox.data.repositories.TagRepository
import com.awesome.zach.jotunheimrsandbox.data.repositories.TaskRepository
import com.awesome.zach.jotunheimrsandbox.data.repositories.TaskTagAssignmentRepository
import java.lang.IllegalArgumentException

class MainViewModel internal constructor(taskRepository: TaskRepository,
                                         projectRepository: ProjectRepository,
                                         tagRepository: TagRepository,
                                         taskTagAssignmentRepository: TaskTagAssignmentRepository,
                                         projectId: Long? = null,
                                         tagId: Long? = null,
                                         taskId: Long? = null) : ViewModel() {
    companion object {
        const val LOG_TAG = "MainViewModel"
    }

    private val tasksList = MediatorLiveData<List<Task>>()
    private val projectsList = MediatorLiveData<List<Project>>()
    private val tagsList = MediatorLiveData<List<Tag>>()

    init {
        setupTasksList(taskRepository, taskTagAssignmentRepository, projectId, tagId)
        setupProjectsList(projectRepository)
        setupTagsList(tagRepository)
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
                // both are null, return all tasks
                val liveTasksList = taskRepository.getAllTasksLive()
                tasksList.addSource(liveTasksList, tasksList::setValue)
            } else {
                // projectId is null, but a tagId was passed, return tasks for that tagId
                val liveTasksList = taskTagAssignmentRepository.getTasksWithTagLive(tagId)
                tasksList.addSource(liveTasksList, tasksList::setValue)
            }
        } else {
            // projectId was passed
            if (tagId == null) {
                // tagId is null, return tasks for the projectId
                val liveTasksList = taskRepository.getTasksForProjectLive(projectId)
                tasksList.addSource(liveTasksList, tasksList::setValue)
            } else {
                // both were passed, return tasks for the projectID but filter for the tags first
                // TODO: implement this, for now throw an exception
                throw IllegalArgumentException("values found for both tagId and projectId. only pass one of them, not both")
            }
        }
    }

    fun getTasks() = tasksList

    fun getProjects() = projectsList

    fun getTags() = tagsList
}