package com.awesome.zach.jotunheimrsandbox.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.awesome.zach.jotunheimrsandbox.AppDatabase
import com.awesome.zach.jotunheimrsandbox.data.entities.Color
import kotlinx.coroutines.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

interface ColorRepository {
    suspend fun newAsync(name: String, hex: String): Deferred<Color>
    suspend fun delete(color: Color)
    suspend fun delete(id: Long)
    fun colors(): LiveData<List<Color>>
    fun colorsNowAsync(): Deferred<List<Color>>
    fun color(): LiveData<Color?>
}

class ColorRepositoryImpl: ColorRepository, KoinComponent {
    private val db by inject<AppDatabase>()
    private val dao = db.getDatabase().colorDao()

    private val colors = dao.list()

    private val colorIdLiveData = MutableLiveData<Long>()
    private val colorLiveData = Transformations.switchMap(db.isDatabaseCreated()) { created ->
        if(created) {
            Transformations.switchMap(colorIdLiveData) { id ->
                dao.colorById(id)
            }
        } else {
            MutableLiveData()
        }
    }

    override suspend fun newAsync(name: String,
                             hex: String): Deferred<Color> {
        return GlobalScope.async(Dispatchers.IO) {
            val ids = dao.insertAll(Color(name = name, hex = hex))
            return@async dao.colorByIdNow(ids.first()) ?: throw RuntimeException("Color cannot be null")
        }
    }

    override suspend fun delete(color: Color) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.delete(color)
        }
    }

    override suspend fun delete(id: Long) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    override fun colors(): LiveData<List<Color>> = colors

    override fun colorsNowAsync(): Deferred<List<Color>> {
        return GlobalScope.async {
            dao.listNow()
        }
    }

    override fun color(): LiveData<Color?> = colorLiveData

}


// import com.awesome.zach.jotunheimrsandbox.data.daos.ColorDao
// import com.awesome.zach.jotunheimrsandbox.data.entities.Color
// import kotlinx.coroutines.Dispatchers.IO
// import kotlinx.coroutines.withContext
//
// class ColorRepository private constructor(
//     private val colorDao: ColorDao
// ){
//
//     suspend fun insertColor(color: Color) : Long {
//         return withContext(IO) {
//             colorDao.insertColor(color)
//         }
//     }
//
//     suspend fun bulkInsertColors(colors: List<Color>) : List<Long> {
//         return withContext(IO) {
//             colorDao.bulkInsertColors(colors)
//         }
//     }
//
//     suspend fun updateColor(color: Color) : Int {
//         return withContext(IO) {
//             colorDao.updateColor(color)
//         }
//     }
//
//     suspend fun bulkUpdateColors(colors: List<Color>) : Int {
//         return withContext(IO) {
//             colorDao.bulkDeleteColors(colors)
//         }
//     }
//
//     suspend fun deleteColor(color: Color) : Int {
//         return withContext(IO) {
//             colorDao.deleteColor(color)
//         }
//     }
//
//     suspend fun bulkDeleteColors(colors: List<Color>) : Int {
//         return withContext(IO) {
//             colorDao.bulkDeleteColors(colors)
//         }
//     }
//
//     suspend fun deleteAllColors() : Int {
//         return withContext(IO) {
//             colorDao.deleteAllColors()
//         }
//     }
//
//     fun list() = colorDao.list()
//
//     suspend fun listNow() : List<Color> {
//         return withContext(IO) {
//             colorDao.listNow()
//         }
//     }
//
//     fun colorByIdNow(id: Long) = colorDao.colorByIdNow(id)
//
//     fun colorByHexNow(hex: String) = colorDao.colorByHexNow(hex)
//
//     fun colorByName(name: String) = colorDao.colorByName(name)
//
//     companion object {
//
//         // for singleton instantiation
//         @Volatile
//         private var instance: ColorRepository? = null
//
//         fun getInstance(colorDao: ColorDao) =
//                 instance ?: synchronized(this) {
//                     instance ?: ColorRepository(colorDao).also { instance = it }
//                 }
//     }
// }