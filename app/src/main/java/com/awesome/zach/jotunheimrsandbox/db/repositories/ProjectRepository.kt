package com.awesome.zach.jotunheimrsandbox.db.repositories

import com.awesome.zach.jotunheimrsandbox.db.daos.ProjectDao
import com.awesome.zach.jotunheimrsandbox.db.entities.Project
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ProjectRepository private constructor(
    private val projectDao: ProjectDao
){

    suspend fun createProject(name: String, colorId: Long) {
        withContext(IO) {
            val project = Project(name = name, colorId = colorId)
            val id = projectDao.insertProject(project)
        }
    }

    suspend fun updateProject(project: Project) {
        withContext(IO) {
            val count = projectDao.updateProject(project)
        }
    }

    suspend fun deleteProject(project: Project) {
        withContext(IO) {
            projectDao.deleteProject(project)
        }
    }

    fun getAllProjects() = projectDao.getAllProjects()

    fun getProjectById(projectId: Long) = projectDao.getProjectById(projectId)

    fun getProjectsByName(name: String) = projectDao.getProjectsByName(name)

    fun getProjectsByColor(colorId: Long) = projectDao.getProjectsByColor(colorId)

    companion object {
        // for singleton instantiation
        @Volatile
        private var instance: ProjectRepository? = null

        fun getInstance(projectDao: ProjectDao) =
                instance ?: synchronized(this) {
                    instance ?: ProjectRepository(projectDao).also { instance = it }
                }
    }
}