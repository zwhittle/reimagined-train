package com.awesome.zach.jotunheimrsandbox.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.awesome.zach.jotunheimrsandbox.data.entities.JHList
import com.awesome.zach.jotunheimrsandbox.data.repositories.ListRepository
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.*

class ListListViewModel(private val listRepository: ListRepository): ViewModel() {

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

    fun lists() = listRepository.lists()

    private val uiNameListLiveData = LiveEvent<((name:String) -> Unit)>()
    fun uiNameList(): LiveEvent<((name: String) -> Unit)> = uiNameListLiveData

    fun onAddListClicked() {
        viewModelScope.launch {
            uiNameListLiveData.postValue { name ->
                viewModelScope.launch(Dispatchers.IO) {
                    newAsync(name).join()
                }
            }
        }
    }

    suspend fun newAsync(name: String): Deferred<JHList> {
        return listRepository.newAsync(name)
    }

    suspend fun delete(list: JHList) {
        listRepository.delete(list)
    }

    suspend fun delete(listId: Long) {
        listRepository.delete(listId)
    }
}