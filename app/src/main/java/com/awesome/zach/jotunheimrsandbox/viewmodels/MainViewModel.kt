package com.awesome.zach.jotunheimrsandbox.viewmodels

import androidx.lifecycle.ViewModel
import com.awesome.zach.jotunheimrsandbox.db.repositories.ProjectRepository
import com.awesome.zach.jotunheimrsandbox.db.repositories.TagRepository
import com.awesome.zach.jotunheimrsandbox.db.repositories.TaskRepository

class MainViewModel internal constructor(
    tagRepository: TagRepository,
    projectRepository: ProjectRepository,
    taskRepository: TaskRepository
) : ViewModel() {

    val tags = tagRepository.getAllTags()

    val projects = projectRepository.getAllProjects()

    val tasks = taskRepository.getActiveTasks()
}