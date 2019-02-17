package com.awesome.zach.jotunheimrsandbox.db.repositories

import com.awesome.zach.jotunheimrsandbox.db.daos.ColorDao
import com.awesome.zach.jotunheimrsandbox.db.entities.Color
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ColorRepository private constructor(
    private val colorDao: ColorDao
){

    suspend fun createColor(color: Color) : Long {
        return withContext(IO) {
            colorDao.insertColor(color)
        }
    }

    suspend fun bulkCreateColors(colors: List<Color>) : List<Long> {
        return withContext(IO) {
            colorDao.insertColors(colors)
        }
    }

    suspend fun updateColor(color: Color) : Int {
        return withContext(IO) {
            colorDao.updateColor(color)
        }
    }

    suspend fun deleteColor(color: Color) {
        withContext(IO) {
            colorDao.deleteColor(color)
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