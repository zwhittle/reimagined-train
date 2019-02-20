package com.awesome.zach.jotunheimrsandbox.viewmodels

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import com.awesome.zach.jotunheimrsandbox.data.repositories.ProjectRepository
import com.awesome.zach.jotunheimrsandbox.data.repositories.TagRepository
import com.awesome.zach.jotunheimrsandbox.data.repositories.TaskRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TagListViewModel internal constructor(private val tagRepository: TagRepository) :
    ViewModel() {

    private val tagsList = MediatorLiveData<List<Tag>>()

    companion object {
        const val LOG_TAG = "TagListViewModel"
    }

    init {
        val liveTagsList = tagRepository.getAllTagsLive()
        tagsList.addSource(
            liveTagsList,
            tagsList::setValue)
    }

    fun getTags() = tagsList

    fun getPHTags(): List<Tag> {
        val tagsAL = ArrayList<Tag>()
        tagsAL.add(Tag(1,
                       "Home",
                       1,
                       "#000000"))
        tagsAL.add(Tag(2,
                       "Office",
                       2,
                       "#ffffff"))
        tagsAL.add(Tag(3,
                       "Low Energy",
                       3,
                       "#ff0000"))
        tagsAL.add(Tag(4,
                       "Med Energy",
                       4,
                       "#00ff00"))
        tagsAL.add(Tag(5,
                       "High Energy",
                       5,
                       "#0000ff"))
        tagsAL.add(Tag(6,
                       "Home",
                       1,
                       "#000000"))
        tagsAL.add(Tag(7,
                       "Office",
                       2,
                       "#ffffff"))
        tagsAL.add(Tag(8,
                       "Low Energy",
                       3,
                       "#ff0000"))
        tagsAL.add(Tag(9,
                       "Med Energy",
                       4,
                       "#00ff00"))
        tagsAL.add(Tag(10,
                       "High Energy",
                       5,
                       "#0000ff"))
        tagsAL.add(Tag(11,
                       "Home",
                       1,
                       "#000000"))
        tagsAL.add(Tag(12,
                       "Office",
                       2,
                       "#ffffff"))
        tagsAL.add(Tag(13,
                       "Low Energy",
                       3,
                       "#ff0000"))
        tagsAL.add(Tag(14,
                       "Med Energy",
                       4,
                       "#00ff00"))
        tagsAL.add(Tag(15,
                       "High Energy",
                       5,
                       "#0000ff"))
        tagsAL.add(Tag(16,
                       "Home",
                       1,
                       "#000000"))
        tagsAL.add(Tag(17,
                       "Office",
                       2,
                       "#ffffff"))
        tagsAL.add(Tag(18,
                       "Low Energy",
                       3,
                       "#ff0000"))
        tagsAL.add(Tag(19,
                       "Med Energy",
                       4,
                       "#00ff00"))
        tagsAL.add(Tag(20,
                       "High Energy",
                       5,
                       "#0000ff"))
        tagsAL.add(Tag(21,
                       "Home",
                       1,
                       "#000000"))
        tagsAL.add(Tag(22,
                       "Office",
                       2,
                       "#ffffff"))
        tagsAL.add(Tag(23,
                       "Low Energy",
                       3,
                       "#ff0000"))
        tagsAL.add(Tag(24,
                       "Med Energy",
                       4,
                       "#00ff00"))
        tagsAL.add(Tag(25,
                       "High Energy",
                       5,
                       "#0000ff"))
        tagsAL.add(Tag(26,
                       "Home",
                       1,
                       "#000000"))
        tagsAL.add(Tag(27,
                       "Office",
                       2,
                       "#ffffff"))
        tagsAL.add(Tag(28,
                       "Low Energy",
                       3,
                       "#ff0000"))
        tagsAL.add(Tag(29,
                       "Med Energy",
                       4,
                       "#00ff00"))
        tagsAL.add(Tag(30,
                       "High Energy",
                       5,
                       "#0000ff"))
        tagsAL.add(Tag(31,
                       "Home",
                       1,
                       "#000000"))
        tagsAL.add(Tag(32,
                       "Office",
                       2,
                       "#ffffff"))
        tagsAL.add(Tag(33,
                       "Low Energy",
                       3,
                       "#ff0000"))
        tagsAL.add(Tag(34,
                       "Med Energy",
                       4,
                       "#00ff00"))
        tagsAL.add(Tag(35,
                       "High Energy",
                       5,
                       "#0000ff"))
        tagsAL.add(Tag(36,
                       "Home",
                       1,
                       "#000000"))
        tagsAL.add(Tag(37,
                       "Office",
                       2,
                       "#ffffff"))
        tagsAL.add(Tag(38,
                       "Low Energy",
                       3,
                       "#ff0000"))
        tagsAL.add(Tag(39,
                       "Med Energy",
                       4,
                       "#00ff00"))
        tagsAL.add(Tag(40,
                       "High Energy",
                       5,
                       "#0000ff"))
        return tagsAL
    }
}