package com.awesome.zach.jotunheimrsandbox.db.repositories

import com.awesome.zach.jotunheimrsandbox.db.daos.ColorDao
import com.awesome.zach.jotunheimrsandbox.db.entities.Color
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ColorRepository private constructor(
    private val colorDao: ColorDao
){

    suspend fun createColor(name: String, hex: String) {
        withContext(IO) {
            val color = Color(name = name, hex = hex)
            val id = colorDao.insertColor(color)
        }
    }

    suspend fun updateColor(color: Color) {
        withContext(IO) {
            val count = colorDao.updateColor(color)
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