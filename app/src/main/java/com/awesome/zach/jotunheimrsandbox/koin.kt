package com.awesome.zach.jotunheimrsandbox

import com.awesome.zach.jotunheimrsandbox.data.repositories.*
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.ListListViewModel
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.ProjectListViewModel
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.TagListViewModel
import com.awesome.zach.jotunheimrsandbox.ui.viewmodels.TaskListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single<AppDatabase> {
        AppDatabaseImpl(androidContext())
    }

    single<ColorRepository> {
        ColorRepositoryImpl()
    }

    single<TagRepository> {
        TagRepositoryImpl()
    }

    single<ListRepository> {
        ListRepositoryImpl()
    }

    single<ProjectRepository> {
        ProjectRepositoryImpl()
    }

    single<TaskRepository> {
        TaskRepositoryImpl()
    }

    viewModel {
        TaskListViewModel(get())
    }

    viewModel {
        ProjectListViewModel(get())
    }

    viewModel {
        ListListViewModel(get())
    }

    viewModel {
        TagListViewModel(get())
    }
}