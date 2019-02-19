package com.awesome.zach.jotunheimrsandbox.db

import android.content.Context
import android.util.Log
import com.awesome.zach.jotunheimrsandbox.db.entities.*
import java.time.LocalDate

class DBSeeder(val db: AppDatabase) {

    var colors = ArrayList<Color>()
    var tags = ArrayList<Tag>()
    var projects = ArrayList<Project>()
    var tasks = ArrayList<Task>()
    var taskTagAssignments = ArrayList<TaskTagAssignment>()

    companion object {
        const val LOG_TAG = "DBSeeder"
    }

    fun populateColorsList() : ArrayList<Color> {
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

        return colors
    }

    fun populateTagsList() : ArrayList<Tag> {
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

        return tags
    }

    fun populateProjectsList() : ArrayList<Project> {
        val colorsFromDao = db.colorDao().getAllColors()
        projects.clear()

        colorsFromDao.forEachIndexed { index, color ->
            val project = Project(name = "Project $index", colorId = color.colorId)
            projects.add(project)
            Log.d(LOG_TAG, "Added $project to projects list")
            System.out.println("Added $project to projects list")
        }

        return projects
    }

    fun populateTasksList(withDates: Boolean) : ArrayList<Task> {
        val projectsFromDao = db.projectDao().getAllProjects()
        tasks.clear()

        if (withDates) {
//            var epochDate = LocalDate.now().minusDays(2)
            var epochDate = LocalDate.parse("2019-02-15")
            projectsFromDao.forEachIndexed { index, project ->
                val endDate = epochDate.plusDays(index.toLong())
                val startDate = epochDate.minusDays(7)
                val task = Task(name = "Task $index", date_start = startDate, date_end = endDate, projectId = project.projectId)
                tasks.add(task)
                Log.d(LOG_TAG, "Added $task to tasks list")
                System.out.println("Added $task to tasks list")
            }

        } else {
            projectsFromDao.forEachIndexed { index, project ->
                val task = Task(name = "Task $index", projectId = project.projectId)
                tasks.add(task)
                Log.d(LOG_TAG, "Added $task to tasks list")
                System.out.println("Added $task to tasks list")
            }
        }

        return tasks
    }

    fun populateTaskTagAssignmentsList() : ArrayList<TaskTagAssignment> {
        val tasksFromDao = db.taskDao().getAllTasks()
        val tagsFromDao = db.tagDao().getAllTags()

        taskTagAssignments.clear()

        tasksFromDao.forEach { task ->
            // home vs office
//            val taskTagJoin1 = TaskTagAssignment(taskId = task.taskId, tagId = tagsFromDao[(0..1).random()].tagId)
            val taskTagJoin1 = TaskTagAssignment(taskId = task.taskId, tagId = tagsFromDao[0].tagId)
            // energy level
//            val taskTagJoin1 = TaskTagAssignment(taskId = task.taskId, tagId = tagsFromDao[(2..4).random()].tagId)
            val taskTagJoin2 = TaskTagAssignment(taskId = task.taskId, tagId = tagsFromDao[2].tagId)

            taskTagAssignments.add(taskTagJoin1)
            taskTagAssignments.add(taskTagJoin2)

            Log.d(LOG_TAG, "Added $taskTagJoin1 to taskTagAssignments list")
            System.out.println("Added $taskTagJoin1 to taskTagAssignments list")
            Log.d(LOG_TAG, "Added $taskTagJoin2 to taskTagAssignments list")
            System.out.println("Added $taskTagJoin2 to taskTagAssignments list")
        }

        return taskTagAssignments
    }
}