package com.awesome.zach.jotunheimrsandbox.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.awesome.zach.jotunheimrsandbox.AppDatabase
import com.awesome.zach.jotunheimrsandbox.data.entities.JHList
import kotlinx.coroutines.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

interface ListRepository {
    suspend fun newAsync(name: String): Deferred<JHList>
    suspend fun delete(list: JHList)
    suspend fun delete(id: Long)
    fun lists(): LiveData<List<JHList>>
    fun listsNowAsync(): Deferred<List<JHList>>
    fun list(): LiveData<JHList?>
}

class ListRepositoryImpl: ListRepository, KoinComponent {

    private val db by inject<AppDatabase>()
    private val dao = db.getDatabase().listDao()

    private val lists = dao.list()

    private val listIdLiveData = MutableLiveData<Long>()
    private val listLiveData = Transformations.switchMap(db.isDatabaseCreated()) { created ->
        if (created) {
            Transformations.switchMap(listIdLiveData) { id ->
                dao.listById(id)
            }
        } else {
            MutableLiveData()
        }
    }

    override suspend fun newAsync(name: String): Deferred<JHList> {
        return GlobalScope.async(Dispatchers.IO) {
            val ids = dao.insertAll(JHList(name))
            return@async dao.listByIdNow(ids.first()) ?: throw RuntimeException("List cannot be null")
        }
    }

    override suspend fun delete(list: JHList) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.delete(list)
        }
    }

    override suspend fun delete(id: Long) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    override fun lists(): LiveData<List<JHList>> = lists

    override fun listsNowAsync(): Deferred<List<JHList>> {
        return GlobalScope.async {
            dao.listNow()
        }
    }

    override fun list(): LiveData<JHList?> = listLiveData
}

// import com.awesome.zach.jotunheimrsandbox.data.daos.ListDao
// import com.awesome.zach.jotunheimrsandbox.data.entities.JHList
// import kotlinx.coroutines.Dispatchers.IO
// import kotlinx.coroutines.withContext
//
// class ListRepository private constructor(private val listDao: ListDao) {
//
//     suspend fun insertList(list: JHList): Long {
//         return withContext(IO) {
//             listDao.insertList(list)
//         }
//     }
//
//     suspend fun bulkInsertJHLists(lists: List<JHList>): List<Long> {
//         return withContext(IO) {
//             listDao.bulkInsertLists(lists)
//         }
//     }
//
//     suspend fun updateJHList(list: JHList): Int {
//         return withContext(IO) {
//             listDao.updateList(list)
//         }
//     }
//
//     suspend fun bulkUpdateJHLists(lists: List<JHList>): Int {
//         return withContext(IO) {
//             listDao.bulkUpdateLists(lists)
//         }
//     }
//
//     suspend fun deleteJHList(list: JHList): Int {
//         return withContext(IO) {
//             listDao.deleteList(list)
//         }
//     }
//
//     suspend fun bulkDeleteJHLists(lists: List<JHList>): Int {
//         return withContext(IO) {
//             listDao.bulkDeleteLists(lists)
//         }
//     }
//
//     suspend fun deleteAllJHLists(): Int {
//         return withContext(IO) {
//             listDao.deleteAllLists()
//         }
//     }
//
//     suspend fun getAllJHLists() : List<JHList> {
//         return withContext(IO) {
//             listDao.getAllLists()
//         }
//     }
//
//     fun getAllListsLive() = listDao.getAllListsLive()
//
//     fun getListById(listId: Long) = listDao.getListByID(listId)
//
//     fun getListByName(name: String) = listDao.getListByName(name)
//
//     companion object {
//         // for singleton instantiation
//         @Volatile
//         private var instance: ListRepository? = null
//
//         fun getInstance(listDao: ListDao) =
//                 instance ?: synchronized(this) {
//                     instance ?: ListRepository(listDao).also { instance = it }
//                 }
//     }
// }