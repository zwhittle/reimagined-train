package com.awesome.zach.jotunheimrsandbox.data.repositories

import com.awesome.zach.jotunheimrsandbox.data.daos.ColorDao
import com.awesome.zach.jotunheimrsandbox.data.entities.Color
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ColorRepository private constructor(
    private val colorDao: ColorDao
){

    suspend fun insertColor(color: Color) : Long {
        return withContext(IO) {
            colorDao.insertColor(color)
        }
    }

    suspend fun bulkInsertColors(colors: List<Color>) : List<Long> {
        return withContext(IO) {
            colorDao.bulkInsertColors(colors)
        }
    }

    suspend fun updateColor(color: Color) : Int {
        return withContext(IO) {
            colorDao.updateColor(color)
        }
    }

    suspend fun bulkUpdateColors(colors: List<Color>) : Int {
        return withContext(IO) {
            colorDao.bulkDeleteColors(colors)
        }
    }

    suspend fun deleteColor(color: Color) : Int {
        return withContext(IO) {
            colorDao.deleteColor(color)
        }
    }

    suspend fun bulkDeleteColors(colors: List<Color>) : Int {
        return withContext(IO) {
            colorDao.bulkDeleteColors(colors)
        }
    }

    suspend fun deleteAllColors() : Int {
        return withContext(IO) {
            colorDao.deleteAllColors()
        }
    }

    fun getAllColors() = colorDao.getAllColors()

    fun getColorById(colorId: Long) = colorDao.getColorById(colorId)

    fun getColorByHex(hex: String) = colorDao.getColorByHex(hex)

    fun getColorByName(name: String) = colorDao.getColorByName(name)

    companion object {

        // for singleton instantiation
        @Volatile
        private var instance: ColorRepository? = null

        fun getInstance(colorDao: ColorDao) =
                instance ?: synchronized(this) {
                    instance ?: ColorRepository(colorDao).also { instance = it }
                }
    }
}