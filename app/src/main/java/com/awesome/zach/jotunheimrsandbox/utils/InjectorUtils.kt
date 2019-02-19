package com.awesome.zach.jotunheimrsandbox.utils

import android.content.Context
import com.awesome.zach.jotunheimrsandbox.db.AppDatabase
import com.awesome.zach.jotunheimrsandbox.db.repositories.ColorRepository

class InjectorUtils {

    private fun getColorRepository(context: Context) : ColorRepository {
        return ColorRepository.getInstance(AppDatabase.getDatabase(context.applicationContext).colorDao())
    }
}