package com.awesome.zach.jotunheimrsandbox.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.awesome.zach.jotunheimrsandbox.AppDatabase
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
import kotlinx.coroutines.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

interface TagRepository {
    suspend fun newAsync(name: String): Deferred<Tag>
    suspend fun delete(tag: Tag)
    suspend fun delete(id: Long)
    fun tags(): LiveData<List<Tag>>
    fun tagsByTask(taskId: Long): LiveData<List<Tag>>
    fun tagsNowAsync(): Deferred<List<Tag>>
    fun tag(): LiveData<Tag?>
}

class TagRepositoryImpl: TagRepository, KoinComponent {

    private val db by inject<AppDatabase>()
    private val dao = db.getDatabase().tagDao()

    private val tags = dao.list()

    private val tagIdLiveData = MutableLiveData<Long>()
    private val tagLiveData = Transformations.switchMap(db.isDatabaseCreated()) { created ->
        if (created) {
            Transformations.switchMap(tagIdLiveData) { id ->
                dao.tagById(id)
            }
        } else {
            MutableLiveData()
        }
    }

    override suspend fun newAsync(name: String): Deferred<Tag> {
        return GlobalScope.async(Dispatchers.IO) {
            val ids = dao.insertAll(Tag(name = name))
            return@async dao.tagByIdNow(ids.first()) ?: throw RuntimeException("Tag cannot be null")
        }
    }

    override suspend fun delete(tag: Tag) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.delete(tag)
        }
    }

    override suspend fun delete(id: Long) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    override fun tags(): LiveData<List<Tag>> = tags

    override fun tagsByTask(taskId: Long): LiveData<List<Tag>> = dao.tagsByTask(taskId)

    override fun tagsNowAsync(): Deferred<List<Tag>> {
        return GlobalScope.async {
            dao.listNow()
        }
    }

    override fun tag(): LiveData<Tag?> = tagLiveData
}

// import android.util.Log
// import com.awesome.zach.jotunheimrsandbox.data.daos.TagDao
// import com.awesome.zach.jotunheimrsandbox.data.entities.Tag
// import kotlinx.coroutines.Dispatchers.IO
// import kotlinx.coroutines.withContext
//
// class TagRepository private constructor(private val tagDao: TagDao) {
//
//     suspend fun insertTag(tag: Tag): Long {
//         return withContext(IO) {
//             tagDao.insertTag(tag)
//         }
//     }
//
//     suspend fun bulkInsertTags(tags: List<Tag>): List<Long> {
//         return withContext(IO) {
//             tagDao.bulkInsertTags(tags)
//         }
//     }
//
//     suspend fun updateTag(tag: Tag): Int {
//         return withContext(IO) {
//             tagDao.updateTag(tag)
//         }
//     }
//
//     suspend fun bulkUpdateTags(tags: List<Tag>): Int {
//         return withContext(IO) {
//             tagDao.bulkUpdateTags(tags)
//         }
//     }
//
//     suspend fun deleteTag(tag: Tag): Int {
//         return withContext(IO) {
//             tagDao.deleteTag(tag)
//         }
//     }
//
//     suspend fun bulkDeleteTags(tags: List<Tag>): Int {
//         return withContext(IO) {
//             tagDao.bulkDeleteTags(tags)
//         }
//     }
//
//     suspend fun deleteAllTags(): Int {
//         return withContext(IO) {
//             tagDao.deleteAllTags()
//         }
//     }
//
//     suspend fun printAllTags() {
//         withContext(IO) {
//             getAllTags().forEach {
//                 Log.d(LOG_TAG, it.toString())
//             }
//         }
//     }
//
//     fun getAllTags() = tagDao.getAllTags()
//
//     fun getAllTagsLive() = tagDao.getAllTagsLive()
//
//     fun getTagById(tagId: Long) = tagDao.getTagById(tagId)
//
//     fun getTagsByName(name: String) = tagDao.getTagsWithName(name)
//
//     fun getTagsByColor(colorId: Long) = tagDao.getTagsWithColor(colorId)
//
//     companion object {
//         const val LOG_TAG = "TagRepository"
//
//         // for singleton instance
//         @Volatile
//         private var instance: TagRepository? = null
//
//         fun getInstance(tagDao: TagDao) =
//             instance ?: synchronized(this) {
//                 instance ?: TagRepository(tagDao).also { instance = it }
//             }
//     }
// }