package com.awesome.zach.jotunheimrsandbox.db.repositories

import com.awesome.zach.jotunheimrsandbox.db.daos.TagDao
import com.awesome.zach.jotunheimrsandbox.db.entities.Tag
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TagRepository private constructor(
    private val tagDao: TagDao
){

    suspend fun createTag(name: String, colorId: Long) : Long{
        return withContext(IO) {
            val tag = Tag(name = name, colorId = colorId)
             tagDao.insertTag(tag)
        }
    }

    suspend fun updateTag(tag: Tag) : Int {
        return withContext(IO) {
            tagDao.updateTag(tag)
        }
    }

    suspend fun deleteTag(tag: Tag) : Int {
        return withContext(IO) {
            tagDao.deleteTag(tag)
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