package com.awesome.zach.jotunheimrsandbox.data

import android.util.Log
import com.awesome.zach.jotunheimrsandbox.BaseDatabase
import com.awesome.zach.jotunheimrsandbox.data.entities.*
import java.time.LocalDate

class DBSeeder(val db: BaseDatabase) {

    var colors = ArrayList<Color>()
    var lists = ArrayList<JHList>()
    var tags = ArrayList<Tag>()
    var projects = ArrayList<Project>()
    var tasks = ArrayList<Task>()
    var taskTagAssignments = ArrayList<TaskTag>()

    companion object {
        const val LOG_TAG = "DBSeeder"
    }

    fun populateColorsList() : ArrayList<Color> {
        colors.clear()
        colors.add(Color(name = "Black", hex = "#000000"))
        colors.add(Color(name = "White", hex = "#ffffff"))
        colors.add(Color(name = "Red", hex = "#ff0000"))
        colors.add(Color(name = "Green", hex = "#00ff00"))
        colors.add(Color(name = "Blue", hex = "#0000ff"))
        colors.add(Color(name = "Yellow", hex = "#ffff00"))
        colors.add(Color(name = "Nice", hex = "#696969"))

        colors.forEach {
            System.out.println("Added $it to colors list")
        }

        return colors
    }

    fun populateListsList() : ArrayList<JHList> {
        lists.clear()
        lists.add(JHList(name = "Next Available Actions"))
        lists.add(JHList(name = "Incubating"))
        lists.add(JHList(name = "Another Time"))

        lists.forEach {
            System.out.println("Added $it to lists list")
        }

        return lists
    }

    fun populateTagsList() : ArrayList<Tag> {
        val colorsFromDao = db.colorDao().listNow()
        tags.clear()
        tags.add(Tag(name = "Home", colorId = colorsFromDao[0].id))
        tags.add(Tag(name = "Office", colorId = colorsFromDao[1].id))
        tags.add(Tag(name = "Low Energy", colorId = colorsFromDao[2].id))
        tags.add(Tag(name = "Med Energy", colorId = colorsFromDao[3].id))
        tags.add(Tag(name = "High Energy", colorId = colorsFromDao[4].id))

        tags.forEach {
            System.out.println("Added $it to tags list")
        }

        return tags
    }

    fun populateProjectsList() : ArrayList<Project> {
        val colorsFromDao = db.colorDao().listNow()
        projects.clear()

        colorsFromDao.forEachIndexed { index, color ->
            val project = Project(name = "Project $index", colorId = color.id)
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
                val task = Task(name = "Task $index", date_start = startDate, date_end = endDate, projectId = project.id)
                tasks.add(task)
                Log.d(LOG_TAG, "Added $task to tasks list")
                System.out.println("Added $task to tasks list")
            }

        } else {
            projectsFromDao.forEachIndexed { index, project ->
                val task = Task(name = "Task $index", projectId = project.id)
                tasks.add(task)
                Log.d(LOG_TAG, "Added $task to tasks list")
                System.out.println("Added $task to tasks list")
            }
        }

        return tasks
    }

    fun populateTaskTagAssignmentsList() : ArrayList<TaskTag> {
        val tasksFromDao = db.taskDao().getAllTasks()
        val tagsFromDao = db.tagDao().getAllTags()

        taskTagAssignments.clear()

        tasksFromDao.forEach { task ->
            // home vs office
//            val taskTagJoin1 = TaskTagAssignment(id = task.id, id = tagsFromDao[(0..1).random()].id)
            val taskTagJoin1 = TaskTag(taskId = task.id, tagId = tagsFromDao[0].id)
            // energy level
//            val taskTagJoin1 = TaskTagAssignment(id = task.id, id = tagsFromDao[(2..4).random()].id)
            val taskTagJoin2 = TaskTag(taskId = task.id, tagId = tagsFromDao[2].id)

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