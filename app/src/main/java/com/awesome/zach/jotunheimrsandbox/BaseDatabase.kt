package com.awesome.zach.jotunheimrsandbox

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.awesome.zach.jotunheimrsandbox.data.Converters
import com.awesome.zach.jotunheimrsandbox.data.daos.*
import com.awesome.zach.jotunheimrsandbox.data.entities.*

@Database(entities = [Color::class, JHList::class, Tag::class, Project::class, Task::class, TaskTag::class],
          version = 1)
@TypeConverters(Converters::class)
abstract class BaseDatabase : RoomDatabase() {
    abstract fun colorDao(): ColorDao
    abstract fun listDao(): ListDao
    abstract fun projectDao(): ProjectDao
    abstract fun tagDao(): TagDao
    abstract fun taskDao(): TaskDao
    abstract fun taskTagDao(): TaskTagDao
}