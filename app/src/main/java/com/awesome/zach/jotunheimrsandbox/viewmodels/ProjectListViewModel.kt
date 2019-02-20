package com.awesome.zach.jotunheimrsandbox.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.awesome.zach.jotunheimrsandbox.data.entities.Project
import com.awesome.zach.jotunheimrsandbox.data.repositories.ProjectRepository

class ProjectListViewModel internal constructor(projectRepository: ProjectRepository) : ViewModel() {

    private val projectsList = MediatorLiveData<List<Project>>()

    companion object {
        const val LOG_TAG = "ProjectListViewModel"
    }

    init {
        val liveProjectsList = projectRepository.getAllProjectsLive()
        projectsList.addSource(liveProjectsList, projectsList::setValue)
    }

    fun getProjects() = projectsList
}