package com.awesome.zach.jotunheimrsandbox.data.repositories

import com.awesome.zach.jotunheimrsandbox.data.daos.ListDao
import com.awesome.zach.jotunheimrsandbox.data.entities.JHList
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ListRepository private constructor(private val listDao: ListDao) {

    suspend fun insertList(list: JHList): Long {
        return withContext(IO) {
            listDao.insertList(list)
        }
    }

    suspend fun bulkInsertJHLists(lists: List<JHList>): List<Long> {
        return withContext(IO) {
            listDao.bulkInsertLists(lists)
        }
    }

    suspend fun updateJHList(list: JHList): Int {
        return withContext(IO) {
            listDao.updateList(list)
        }
    }

    suspend fun bulkUpdateJHLists(lists: List<JHList>): Int {
        return withContext(IO) {
            listDao.bulkUpdateLists(lists)
        }
    }

    suspend fun deleteJHList(list: JHList): Int {
        return withContext(IO) {
            listDao.deleteList(list)
        }
    }

    suspend fun bulkDeleteJHLists(lists: List<JHList>): Int {
        return withContext(IO) {
            listDao.bulkDeleteLists(lists)
        }
    }

    suspend fun deleteAllJHLists(): Int {
        return withContext(IO) {
            listDao.deleteAllLists()
        }
    }

    suspend fun getAllJHLists() : List<JHList> {
        return withContext(IO) {
            listDao.getAllLists()
        }
    }

    fun getAllListsLive() = listDao.getAllListsLive()

    fun getListById(listId: Long) = listDao.getListByID(listId)

    fun getListByName(name: String) = listDao.getListByName(name)

    companion object {
        // for singleton instantiation
        @Volatile
        private var instance: ListRepository? = null

        fun getInstance(listDao: ListDao) =
                instance ?: synchronized(this) {
                    instance ?: ListRepository(listDao).also { instance = it }
                }
    }
}