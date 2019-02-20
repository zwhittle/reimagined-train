package com.awesome.zach.jotunheimrsandbox.utils

import android.content.Context
import com.awesome.zach.jotunheimrsandbox.data.AppDatabase
import com.awesome.zach.jotunheimrsandbox.data.repositories.*
import com.awesome.zach.jotunheimrsandbox.viewmodels.TagListViewModelFactory

object InjectorUtils {

    private fun getColorRepository(context: Context) : ColorRepository {
        return ColorRepository.getInstance(AppDatabase.getDatabase(context.applicationContext).colorDao())
    }
    
    private fun getTagRepository(context: Context) : TagRepository {
        return TagRepository.getInstance(AppDatabase.getDatabase(context.applicationContext).tagDao())
    }

    fun provideTagListViewModelFactory(context: Context) : TagListViewModelFactory {
        val repository = getTagRepository(context)
        return TagListViewModelFactory(repository)
    }

    private fun getProjectRepository(context: Context) : ProjectRepository {
        return ProjectRepository.getInstance(AppDatabase.getDatabase(context.applicationContext).projectDao())
    }

    private fun getTaskRepository(context: Context) : TaskRepository {
        return TaskRepository.getInstance(AppDatabase.getDatabase(context.applicationContext).taskDao())
    }
    private fun getTaskTagAssignmentRepository(context: Context) : TaskTagAssignmentRepository {
        return TaskTagAssignmentRepository.getInstance(AppDatabase.getDatabase(context.applicationContext).taskTagAssignmentDao())
    }
    
}