package com.awesome.zach.jotunheimrsandbox.db

import com.awesome.zach.jotunheimrsandbox.db.daos.ColorDao
import com.awesome.zach.jotunheimrsandbox.db.entities.Color

class DBSeeder {

    val colors  = ArrayList<Color>()

    fun seedColors(colorDao: ColorDao) {
        populateColorsList()
        colors.forEach {
            colorDao.insertColor(it)
        }
    }

    private fun populateColorsList() {
        colors.add(Color(name = "Black", hex = "#000000"))
        colors.add(Color(name = "White", hex = "#ffffff"))
        colors.add(Color(name = "Red", hex = "#ff0000"))
        colors.add(Color(name = "Green", hex = "#00ff00"))
        colors.add(Color(name = "Yellow", hex = "#0000ff"))
    }
}