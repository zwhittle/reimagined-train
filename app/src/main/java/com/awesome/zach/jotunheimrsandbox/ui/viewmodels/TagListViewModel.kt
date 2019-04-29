package com.awesome.zach.jotunheimrsandbox.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.data.repositories.TagRepository
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.*

class TagListViewModel(private val tagRepository: TagRepository): ViewModel() {

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

    fun tags() = tagRepository.tags()

    fun tagsByTask(taskId: Long) = tagRepository.tagsByTask(taskId)

    private val uiNameTagLiveData = LiveEvent<((name: String) -> Unit)>()
    fun uiNameTag(): LiveEvent<((name: String) -> Unit)> = uiNameTagLiveData

    fun onAddTagClicked() {
        viewModelScope.launch {
            uiNameTagLiveData.postValue { name ->
                viewModelScope.launch(Dispatchers.IO) {
                    newAsync(name).join()
                }
            }
        }
    }

    suspend fun newAsync(name: String): Deferred<Tag> {
        return tagRepository.newAsync(name)
    }

    suspend fun delete(tag: Tag) {
        tagRepository.delete(tag)
    }

    suspend fun delete(tagId: Long) {
        tagRepository.delete(tagId)
    }
}