package com.awesome.zach.jotunheimrsandbox

import com.awesome.zach.jotunheimrsandbox.data.repositories.*
import org.koin.android.ext.koin.androidContext
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
}