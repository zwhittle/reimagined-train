package com.awesome.zach.jotunheimrsandbox.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.awesome.zach.jotunheimrsandbox.data.entities.Project
import com.awesome.zach.jotunheimrsandbox.data.repositories.ProjectRepository
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.*

class ProjectListViewModel(private val projectRepository: ProjectRepository): ViewModel() {

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

    fun projects() = projectRepository.projects()

    private val uiNameProjectLiveData = LiveEvent<((name: String) -> Unit)>()
    fun uiNameProject(): LiveEvent<((name: String) -> Unit)> = uiNameProjectLiveData

    fun onAddProjectClicked() {
        viewModelScope.launch {
            uiNameProjectLiveData.postValue { name ->
                viewModelScope.launch(Dispatchers.IO) {
                    newAsync(name).join()
                }
            }
        }
    }

    suspend fun newAsync(name: String): Deferred<Project> {
        return projectRepository.newAsync(name)
    }

    suspend fun delete(project: Project) {
        projectRepository.delete(project)
    }

    suspend fun delete(projectId: Long) {
        projectRepository.delete(projectId)
    }
}