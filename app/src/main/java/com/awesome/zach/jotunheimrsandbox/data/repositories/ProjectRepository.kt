package com.awesome.zach.jotunheimrsandbox.data.repositories

import com.awesome.zach.jotunheimrsandbox.data.daos.ProjectDao
import com.awesome.zach.jotunheimrsandbox.data.entities.Project
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ProjectRepository private constructor(private val projectDao: ProjectDao) {

    suspend fun insertProject(project: Project): Long {
        return withContext(IO) {
            projectDao.insertProject(project)
        }
    }

    suspend fun bulkInsertProjects(projects: List<Project>): List<Long> {
        return withContext(IO) {
            projectDao.bulkInsertProjects(projects)
        }
    }

    suspend fun updateProject(project: Project): Int {
        return withContext(IO) {
            projectDao.updateProject(project)
        }
    }

    suspend fun bulkUpdateProjects(projects: List<Project>): Int {
        return withContext(IO) {
            projectDao.bulkUpdateProjects(projects)
        }
    }

    suspend fun deleteProject(project: Project): Int {
        return withContext(IO) {
            projectDao.deleteProject(project)
        }
    }

    suspend fun bulkDeleteProjects(projects: List<Project>): Int {
        return withContext(IO) {
            projectDao.bulkDeleteProjects(projects)
        }
    }

    suspend fun deleteAllProjects(): Int {
        return withContext(IO) {
            projectDao.deleteAllProjects()
        }
    }

    suspend fun getAllProjects() : List<Project> {
        return withContext(IO) {
            projectDao.getAllProjects()
        }
    }

    fun getAllProjectsLive() = projectDao.getAllProjectsLive()

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