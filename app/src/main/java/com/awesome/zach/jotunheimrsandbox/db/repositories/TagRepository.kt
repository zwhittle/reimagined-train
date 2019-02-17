package com.awesome.zach.jotunheimrsandbox.db.repositories

import com.awesome.zach.jotunheimrsandbox.db.daos.TagDao
import com.awesome.zach.jotunheimrsandbox.db.entities.Tag
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TagRepository private constructor(
    private val tagDao: TagDao
){

    suspend fun insertTag(tag: Tag) : Long {
        return withContext(IO) {
             tagDao.insertTag(tag)
        }
    }

    suspend fun bulkInsertTags(tags: List<Tag>) : List<Long> {
        return withContext(IO) {
            tagDao.bulkInsertTags(tags)
        }
    }

    suspend fun updateTag(tag: Tag) : Int {
        return withContext(IO) {
            tagDao.updateTag(tag)
        }
    }

    suspend fun bulkUpdateTags(tags: List<Tag>) : Int {
        return withContext(IO) {
            tagDao.bulkUpdateTags(tags)
        }
    }

    suspend fun deleteTag(tag: Tag) : Int {
        return withContext(IO) {
            tagDao.deleteTag(tag)
        }
    }

    suspend fun bulkDeleteTags(tags: List<Tag>) : Int {
        return withContext(IO) {
            tagDao.bulkDeleteTags(tags)
        }
    }

    suspend fun deleteAllTags() : Int {
        return withContext(IO) {
            tagDao.deleteAllTags()
        }
    }

    fun getAllTags() = tagDao.getAllTags()

    fun getTagById(tagId: Long) = tagDao.getTagById(tagId)

    fun getTagsByName(name: String) = tagDao.getTagsByName(name)

    fun getTagsByColor(colorId: Long) = tagDao.getTagsByColor(colorId)

    companion object {

        // for singleton instance
        @Volatile
        private var instance: TagRepository? = null

        fun getInstance(tagDao: TagDao) =
                instance ?: synchronized(this) {
                    instance ?: TagRepository(tagDao).also { instance = it }
                }
    }
}