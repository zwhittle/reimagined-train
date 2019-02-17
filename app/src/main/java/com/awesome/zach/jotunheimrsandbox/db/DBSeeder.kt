package com.awesome.zach.jotunheimrsandbox.db

import android.util.Log
import com.awesome.zach.jotunheimrsandbox.db.entities.*
import java.util.*
import kotlin.collections.ArrayList

class DBSeeder(val db: AppDatabase) {

    val colors  = ArrayList<Color>()
    val tags = ArrayList<Tag>()
    val projects = ArrayList<Project>()
    val tasks = ArrayList<Task>()
    val taskTagJoins = ArrayList<TaskTagJoin>()

    companion object {
        const val LOG_TAG = "DBSeeder"
    }

    fun populateColorsList() {
        colors.clear()
        colors.add(Color(name = "Black", hex = "#000000"))
        colors.add(Color(name = "White", hex = "#ffffff"))
        colors.add(Color(name = "Red", hex = "#ff0000"))
        colors.add(Color(name = "Green", hex = "#00ff00"))
        colors.add(Color(name = "Yellow", hex = "#0000ff"))
        colors.add(Color(name = "Nice", hex = "#696969"))

        colors.forEach {
            System.out.println("Added $it to colors list")
        }
    }

    fun populateTagsList() {
        val colorsFromDao = db.colorDao().getAllColors()
        tags.clear()
        tags.add(Tag(name = "Home", colorId = colorsFromDao[0].colorId))
        tags.add(Tag(name = "Office", colorId = colorsFromDao[1].colorId))
        tags.add(Tag(name = "Low Energy", colorId = colorsFromDao[2].colorId))
        tags.add(Tag(name = "Med Energy", colorId = colorsFromDao[3].colorId))
        tags.add(Tag(name = "High Energy", colorId = colorsFromDao[4].colorId))
        
        tags.forEach { 
            System.out.println("Added $it to tags list")
        }
    }

    fun populateProjectsList() {
        val colorsFromDao = db.colorDao().getAllColors()
        projects.clear()

        colorsFromDao.forEachIndexed { index, color ->
            val project = Project(name = "Project $index", colorId = color.colorId)
            projects.add(project)
            Log.d(LOG_TAG, "Added $project to projects list")
            System.out.println("Added $project to projects list")
        }
    }

    fun populateTasksList(withDates: Boolean) {
        val projectsFromDao = db.projectDao().getAllProjects()
        tasks.clear()

        if (withDates) {
            projectsFromDao.forEachIndexed { index, project ->
                val task = Task(name = "Task $index", date_start = Date(), date_end = Date(), projectId = project.projectId)
                tasks.add(task)
                Log.d(LOG_TAG, "Added $task to tasks list")
                System.out.println("Added $task to tasks list")
            }
        } else{
            projectsFromDao.forEachIndexed { index, project ->
                val task = Task(name = "Task $index", projectId = project.projectId)
                tasks.add(task)
                Log.d(LOG_TAG, "Added $task to tasks list")
                System.out.println("Added $task to tasks list")
            }
        }
    }

    fun populateTaskTagJoinList() {
        val tasksFromDao = db.taskDao().getAllTasks()
        val tagsFromDao = db.tagDao().getAllTags()

        taskTagJoins.clear()

        tasksFromDao.forEachIndexed{ index, task ->
            // home vs office
//            val taskTagJoin1 = TaskTagJoin(taskId = task.taskId, tagId = tagsFromDao[(0..1).random()].tagId)
            val taskTagJoin1 = TaskTagJoin(taskId = task.taskId, tagId = tagsFromDao[0].tagId)
            // energy level
//            val taskTagJoin1 = TaskTagJoin(taskId = task.taskId, tagId = tagsFromDao[(2..4).random()].tagId)
            val taskTagJoin2 = TaskTagJoin(taskId = task.taskId, tagId = tagsFromDao[2].tagId)

            taskTagJoins.add(taskTagJoin1)
            taskTagJoins.add(taskTagJoin2)

            Log.d(LOG_TAG, "Added $taskTagJoin1 to tasktagjoins list")
            System.out.println("Added $taskTagJoin1 to taskTagJoins list")
            Log.d(LOG_TAG, "Added $taskTagJoin2 to tasktagjoins list")
            System.out.println("Added $taskTagJoin2 to taskTagJoins list")
        }
    }
}