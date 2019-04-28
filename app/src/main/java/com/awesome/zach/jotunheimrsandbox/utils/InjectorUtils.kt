package com.awesome.zach.jotunheimrsandbox.utils

import android.content.Context
import com.awesome.zach.jotunheimrsandbox.AppDatabase
import com.awesome.zach.jotunheimrsandbox.data.AppDatabase
import com.awesome.zach.jotunheimrsandbox.data.repositories.*
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.MainViewModelFactory

object InjectorUtils {

    private fun getColorRepository(context: Context): ColorRepository {
        return ColorRepository.getInstance(
            AppDatabase.getDatabase(context.applicationContext).colorDao())
    }

    private fun getListRepository(context: Context) : ListRepository {
        return ListRepository.getInstance(
            AppDatabase.getDatabase(context.applicationContext).listDao())
    }

    private fun getTagRepository(context: Context): TagRepository {
        return TagRepository.getInstance(
            AppDatabase.getDatabase(context.applicationContext).tagDao())
    }

    fun provideMainViewModelFactory(context: Context, projectId: Long? = null, listId: Long? = null, taskId: Long? = null,
                                    tagId: Long? = null): MainViewModelFactory {

        val colorRepository = getColorRepository(context)
        val listRepository = getListRepository(context)
        val taskRepository = getTaskRepository(context)
        val projectRepository = getProjectRepository(context)
        val tagRepository = getTagRepository(context)
        val taskTagAssignmentRepository = getTaskTagAssignmentRepository(context)
        return MainViewModelFactory(colorRepository = colorRepository,
                                    listRepository = listRepository,
                                    taskRepository = taskRepository,
                                    projectRepository = projectRepository,
                                    tagRepository = tagRepository,
                                    taskTagAssignmentRepository = taskTagAssignmentRepository,
                                    projectId = projectId, taskId = taskId, listId = listId, tagId = tagId)
    }

    private fun getProjectRepository(context: Context): ProjectRepository {
        return ProjectRepository.getInstance(
            AppDatabase.getDatabase(context.applicationContext).projectDao())
    }

    private fun getTaskRepository(context: Context): TaskRepository {
        return TaskRepository.getInstance(
            AppDatabase.getDatabase(context.applicationContext).taskDao())
    }

    private fun getTaskTagAssignmentRepository(context: Context): TaskTagAssignmentRepository {
        return TaskTagAssignmentRepository.getInstance(
            AppDatabase.getDatabase(context.applicationContext).taskTagAssignmentDao())
    }
}