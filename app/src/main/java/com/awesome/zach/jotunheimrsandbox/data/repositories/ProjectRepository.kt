package com.awesome.zach.jotunheimrsandbox.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.awesome.zach.jotunheimrsandbox.AppDatabase
import com.awesome.zach.jotunheimrsandbox.data.entities.Project
import kotlinx.coroutines.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

interface ProjectRepository {
    suspend fun newAsync(name: String): Deferred<Project>
    suspend fun delete(project: Project)
    suspend fun delete(id: Long)
    fun projects(): LiveData<List<Project>>
    fun projectsNowAsync(): Deferred<List<Project>>
    fun project(): LiveData<Project?>
}

class ProjectRepositoryImpl: ProjectRepository, KoinComponent {

    private val db by inject<AppDatabase>()
    private val dao = db.getDatabase().projectDao()

    private val projects = dao.list()

    private val projectIdLiveData = MutableLiveData<Long>()
    private val projectLiveData = Transformations.switchMap(db.isDatabaseCreated()) { created ->
        if (created) {
            Transformations.switchMap(projectIdLiveData) { id ->
                dao.projectById(id)
            }
        } else {
            MutableLiveData()
        }
    }

    override suspend fun newAsync(name: String): Deferred<Project> {
        return GlobalScope.async(Dispatchers.IO) {
            val ids = dao.insertAll(Project(name))
            return@async dao.projectByIdNow(ids.first()) ?: throw RuntimeException("Project cannot be null")
        }
    }

    override suspend fun delete(project: Project) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.delete(project)
        }
    }

    override suspend fun delete(id: Long) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    override fun projects(): LiveData<List<Project>> = projects

    override fun projectsNowAsync(): Deferred<List<Project>> {
        return GlobalScope.async {
            dao.listNow()
        }
    }

    override fun project(): LiveData<Project?> = projectLiveData

}

// import com.awesome.zach.jotunheimrsandbox.data.daos.ProjectDao
// import com.awesome.zach.jotunheimrsandbox.data.entities.Project
// import kotlinx.coroutines.Dispatchers.IO
// import kotlinx.coroutines.withContext
//
// class ProjectRepository private constructor(private val projectDao: ProjectDao) {
//
//     suspend fun insertProject(project: Project): Long {
//         return withContext(IO) {
//             projectDao.insertProject(project)
//         }
//     }
//
//     suspend fun bulkInsertProjects(projects: List<Project>): List<Long> {
//         return withContext(IO) {
//             projectDao.bulkInsertProjects(projects)
//         }
//     }
//
//     suspend fun updateProject(project: Project): Int {
//         return withContext(IO) {
//             projectDao.updateProject(project)
//         }
//     }
//
//     suspend fun bulkUpdateProjects(projects: List<Project>): Int {
//         return withContext(IO) {
//             projectDao.bulkUpdateProjects(projects)
//         }
//     }
//
//     suspend fun deleteProject(project: Project): Int {
//         return withContext(IO) {
//             projectDao.deleteProject(project)
//         }
//     }
//
//     suspend fun bulkDeleteProjects(projects: List<Project>): Int {
//         return withContext(IO) {
//             projectDao.bulkDeleteProjects(projects)
//         }
//     }
//
//     suspend fun deleteAllProjects(): Int {
//         return withContext(IO) {
//             projectDao.deleteAllProjects()
//         }
//     }
//
//     suspend fun getAllProjects() : List<Project> {
//         return withContext(IO) {
//             projectDao.getAllProjects()
//         }
//     }
//
//     fun getAllProjectsLive() = projectDao.getAllProjectsLive()
//
//     fun getProjectById(projectId: Long) = projectDao.getProjectById(projectId)
//
//     fun getProjectsByName(name: String) = projectDao.getProjectsByName(name)
//
//     fun getProjectsByColor(colorId: Long) = projectDao.getProjectsByColor(colorId)
//
//     companion object {
//         // for singleton instantiation
//         @Volatile
//         private var instance: ProjectRepository? = null
//
//         fun getInstance(projectDao: ProjectDao) =
//             instance ?: synchronized(this) {
//                 instance ?: ProjectRepository(projectDao).also { instance = it }
//             }
//     }
// }