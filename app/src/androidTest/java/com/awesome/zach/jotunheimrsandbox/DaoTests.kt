package com.awesome.zach.jotunheimrsandbox

import android.arch.persistence.room.Room
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.awesome.zach.jotunheimrsandbox.db.AppDatabase
import com.awesome.zach.jotunheimrsandbox.db.DBSeeder
import com.awesome.zach.jotunheimrsandbox.db.daos.*
import com.awesome.zach.jotunheimrsandbox.db.entities.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.util.*

class DaoTests {
    private lateinit var colorDao: ColorDao
    private lateinit var tagDao: TagDao
    private lateinit var projectDao: ProjectDao
    private lateinit var taskDao: TaskDao
    private lateinit var taskTagJoinDao: TaskTagJoinDao
    private lateinit var db: AppDatabase

    private lateinit var dbSeeder: DBSeeder

    companion object {
        const val KEY_INSERTED = "key_inserted"
        const val KEY_RETURNED = "key_returned"
    }

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        colorDao = db.colorDao()
        tagDao = db.tagDao()
        projectDao = db.projectDao()
        taskDao = db.taskDao()
        taskTagJoinDao = db.taskTagJoinDao()

        dbSeeder = DBSeeder(db)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /**
     * ColorDao tests and helpers
     */
    // Writes one color to the DB
    @Test
    @Throws(Exception::class)
    fun writeColorDaoTest() {
        val compareValues = writeColor()
        assert(compareValues[KEY_INSERTED] == compareValues[KEY_RETURNED])
    }

    // inserts a list of colors into the DB and compares the count of before to the count after
    @Test
    @Throws(Exception::class)
    fun bulkWriteColorsDaoTest() {
        val compareValues = bulkWriteColors()
        assert(compareValues[KEY_INSERTED] == compareValues[KEY_RETURNED])
    }

    @Test
    @Throws(Exception::class)
    fun updateColorDaoTest() {
        val color = writeColor()[KEY_RETURNED]
        if (color != null) {
            color.name = "Lol Pink Or Whatever"
            color.hex = "#696969"
            val updatedCount = colorDao.updateColor(color)
            assert(updatedCount == 1)
        } else {
            throw Exception("color is null")
        }
    }
    
    @Test
    @Throws(Exception::class)
    fun bulkUpdateColorDaoTest() {
        bulkWriteColors()
        val colors = colorDao.getAllColors()
        colors.forEach { color ->
            color.name = "Lol Pink Or Whatever"
            color.hex = "#696969"
        }

        val updatedCount = colorDao.bulkUpdateColors(colors)
        assert(updatedCount == colors.size)
    }

    @Test
    @Throws(Exception::class)
    fun deleteColorDaoTest() {
        val color = writeColor()[KEY_RETURNED]
        if (color != null) {
            val deletedCount = colorDao.deleteColor(color)
            assert(deletedCount == 1)
        } else {
            throw Exception("color is null")
        }
    }

    @Test
    @Throws(Exception::class)
    fun bulkDeleteColorsDaoTest() {
        bulkWriteColors()
        val colors = colorDao.getAllColors()
        val deletedCount = colorDao.bulkDeleteColors(colors)

        assert(colors.size == deletedCount)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllColorsDaoTest() {
        bulkWriteColors()
        val colors = colorDao.getAllColors()
        val deletedCount = colorDao.deleteAllColors()

        val countsMatch = deletedCount == colors.size
        val noMoreColors = colorDao.getAllColors().isEmpty()

        assert(countsMatch && noMoreColors)
    }

    // helper function that can be called to add a color to the db without calling the full test
    private fun writeColor(): Map<String, Color> {
        dbSeeder.populateColorsList()
        val colorToInsert: Color = dbSeeder.colors[0]
        val returnedId = colorDao.insertColor(colorToInsert)
        val returnedColor = colorDao.getColorById(returnedId)

        val compareValues = mutableMapOf<String, Color>()
        compareValues[KEY_INSERTED] = colorToInsert
        compareValues[KEY_RETURNED] = returnedColor
        return compareValues
    }

    // helper function that can be called to add a list of colors to the db without calling the full test
    private fun bulkWriteColors(): Map<String, Int> {
        dbSeeder.populateColorsList()
        val colorsToInsert = dbSeeder.colors
        val returnedIds = colorDao.insertColors(colorsToInsert)

        val compareValues = mutableMapOf<String, Int>()
        compareValues[KEY_INSERTED] = colorsToInsert.size
        compareValues[KEY_RETURNED] = returnedIds.size
        return compareValues
    }

    /**
     * TagDao tests and helpers
     */

    @Test
    @Throws(Exception::class)
    fun writeTagDaoTest() {
        val compareValues = writeTag()
        assert(compareValues[KEY_INSERTED] == compareValues[KEY_RETURNED])
    }

    @Test
    @Throws(Exception::class)
    fun bulkWriteTagsDaoTest() {
        val compareValues = bulkWriteTags()
        assert(compareValues[KEY_INSERTED] == compareValues[KEY_RETURNED])
    }

    @Test
    @Throws(Exception::class)
    fun updateTagDaoTest() {
        val tag = writeTag()[KEY_RETURNED]

        if (tag != null) {
            tag.name = "Nice"
            tag.colorId = colorDao.getColorByHex("#696969").colorId
            val updatedCount = tagDao.updateTag(tag)
            assert(updatedCount == 1)
        } else {
            throw Exception("startTag is null")
        }
    }

    @Test
    @Throws(Exception::class)
    fun bulkUpdateTagDaoTest() {
        bulkWriteTags()
        val tags = tagDao.getAllTags()
        tags.forEach { tag ->
            tag.name = "Nice"
            tag.colorId = colorDao.getColorByHex("#696969").colorId
        }

        val updatedCount = tagDao.bulkUpdateTags(tags)
        assert(updatedCount == tags.size)
    }

    @Test
    @Throws(Exception::class)
    fun deleteTagDaoTest() {
        val tag = writeTag()[KEY_RETURNED]
        if (tag != null) {
            val deletedCount = tagDao.deleteTag(tag)
            assert(deletedCount == 1)
        } else {
            throw Exception("tag is null")
        }
    }

    @Test
    @Throws(Exception::class)
    fun bulkDeleteTagsDaoTest() {
        bulkWriteTags()
        val tags = tagDao.getAllTags()
        val deletedCount = tagDao.bulkDeleteTags(tags)

        assert(tags.size == deletedCount)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllTagsDaoTest() {
        bulkWriteTags()
        val tags = tagDao.getAllTags()
        val deletedCount = tagDao.deleteAllTags()

        val countsMatch = deletedCount == tags.size
        val noMoreTags = tagDao.getAllTags().isEmpty()

        assert(countsMatch && noMoreTags)
    }

    // helper function that can be called to add a tag to the db without calling the full test
    private fun writeTag(): Map<String, Tag> {
        bulkWriteColors()
        dbSeeder.populateTagsList()

        val tagToInsert = dbSeeder.tags[0]
        val returnedId = tagDao.insertTag(tagToInsert)
        val returnedTag = tagDao.getTagById(returnedId)

        val compareValues = mutableMapOf<String, Tag>()
        compareValues[KEY_INSERTED] = tagToInsert
        compareValues[KEY_RETURNED] = returnedTag
        return compareValues
    }

    // helper function that can be called to add a list of tags to the db without calling the full test
    private fun bulkWriteTags(): Map<String, Int> {
        bulkWriteColors()
        dbSeeder.populateTagsList()

        val tagsToInsert = dbSeeder.tags
        val returnedIds = tagDao.insertTags(tagsToInsert)

        val compareValues = mutableMapOf<String, Int>()
        compareValues[KEY_INSERTED] = tagsToInsert.size
        compareValues[KEY_RETURNED] = returnedIds.size
        return compareValues
    }

    /**
     * ProjectDao tests and helpers
     */
    @Test
    @Throws(Exception::class)
    fun writeProjectDaoTest() {
        val compareValues = writeProject()
        assert(compareValues[KEY_INSERTED] == compareValues[KEY_RETURNED])
    }

    @Test
    @Throws(Exception::class)
    fun bulkWriteProjectsDaoTest() {
        val compareValues = bulkWriteProjects()
        assert(compareValues[KEY_INSERTED] == compareValues[KEY_RETURNED])
    }

    @Test
    @Throws(Exception::class)
    fun updateProjectDaoTest() {
        val project = writeProject()[KEY_RETURNED]

        if (project != null) {
            project.name = "Nice"
            project.colorId = colorDao.getColorByHex("#696969").colorId
            val updatedCount = projectDao.updateProject(project)
            assert(updatedCount == 1)
        } else {
            throw Exception("project is null")
        }
    }

    @Test
    @Throws(Exception::class)
    fun bulkUpdateProjectDaoTest() {
        bulkWriteProjects()
        val projects = projectDao.getAllProjects()
        projects.forEach { project ->
            project.name = "Nice"
            project.colorId = colorDao.getColorByHex("#696969").colorId
        }

        val updatedCount = projectDao.bulkUpdateProjects(projects)
        assert(updatedCount == projects.size)
    }

    @Test
    @Throws(Exception::class)
    fun deleteProjectDaoTest() {
        val project = writeProject()[KEY_RETURNED]
        if (project != null) {
            val deletedCount = projectDao.deleteProject(project)
            assert(deletedCount == 1)
        } else {
            throw Exception("project is null")
        }
    }

    @Test
    @Throws(Exception::class)
    fun bulkDeleteProjectsDaoTest() {
        bulkWriteProjects()
        val projects = projectDao.getAllProjects()
        val deletedCount = projectDao.bulkDeleteProjects(projects)

        assert(projects.size == deletedCount)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllProjectsDaoTest() {
        bulkWriteProjects()
        val projects = projectDao.getAllProjects()
        val deletedCount = projectDao.deleteAllProjects()

        val countsMatch = deletedCount == projects.size
        val noMoreProjects = projectDao.getAllProjects().isEmpty()

        assert(countsMatch && noMoreProjects)
    }

    // helper function that can be called to add a project to the db without calling the full test
    private fun writeProject(): Map<String, Project> {
        bulkWriteColors()
        dbSeeder.populateProjectsList()

        val projectToInsert = dbSeeder.projects[0]
        val returnedId = projectDao.insertProject(projectToInsert)
        val returnedProject = projectDao.getProjectById(returnedId)

        val compareValues = mutableMapOf<String, Project>()
        compareValues[KEY_INSERTED] = projectToInsert
        compareValues[KEY_RETURNED] = returnedProject
        return compareValues
    }

    // helper function that can be called to add a list of projects to the db without calling the full test
    private fun bulkWriteProjects(): Map<String, Int> {
        bulkWriteColors()
        dbSeeder.populateProjectsList()

        val projectsToInsert = dbSeeder.projects
        val returnedIds = projectDao.insertProjects(projectsToInsert)

        val compareValues = mutableMapOf<String, Int>()
        compareValues[KEY_INSERTED] = projectsToInsert.size
        compareValues[KEY_RETURNED] = returnedIds.size
        return compareValues
    }

    /**
     * TaskDao tests and helpers
     */
    @Test
    @Throws(Exception::class)
    fun writeTaskWithoutDatesDaoTest() {
        val compareValues = writeTask(false)
        assert(compareValues[KEY_INSERTED] == compareValues[KEY_RETURNED])
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun writeTaskWithDatesDaoTest() {
        val compareValues = writeTask(true)
        assert(compareValues[KEY_INSERTED] == compareValues[KEY_RETURNED])
    }


    @Test
    @Throws(Exception::class)
    fun bulkWriteTasksWithoutDatesDaoTest() {
        val compareValues = bulkWriteTasks(false)
        assert(compareValues[KEY_INSERTED] == compareValues[KEY_RETURNED])
    }

    @Test
    @Throws(Exception::class)
    fun bulkWriteTasksWithDatesDaoTest() {
        val compareValues = bulkWriteTasks(true)
        assert(compareValues[KEY_INSERTED] == compareValues[KEY_RETURNED])
    }

    @Test
    @Throws(Exception::class)
    fun updateTaskDaoTest() {
        val task = writeTask(false)[KEY_RETURNED]

        if (task != null) {
            task.name = "Nice"
            task.date_start = Date()
            task.date_end = Date()
            task.projectId = projectDao.getProjectsByName("Project 1")[0].projectId
            val updatedCount = taskDao.updateTask(task)
            assert(updatedCount == 1)
        } else {
            throw Exception("task is null")
        }
    }

    @Test
    @Throws(Exception::class)
    fun bulkUpdateTaskDaoTest() {
        bulkWriteTasks(false)
        val startingTasks = taskDao.getAllTasks()
        startingTasks.forEach { task ->
            task.name = "Nice"
            task.date_start = Date()
            task.date_end = Date()
            task.projectId = projectDao.getProjectsByName("Project 1")[0].projectId
        }

        val updatedCount = taskDao.bulkUpdateTasks(startingTasks)
        assert(updatedCount == startingTasks.size)
    }

    @Test
    @Throws(Exception::class)
    fun deleteTaskDaoTest() {
        val task = writeTask(false)[KEY_RETURNED]
        if (task != null) {
            val deletedCount = taskDao.deleteTask(task)
            assert(deletedCount == 1)
        } else {
            throw Exception("task is null")
        }
    }

    @Test
    @Throws(Exception::class)
    fun bulkDeleteTasksDaoTest() {
        bulkWriteTasks(false)
        val tasks = taskDao.getAllTasks()
        val deletedCount = taskDao.bulkDeleteTasks(tasks)

        assert(tasks.size == deletedCount)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllTasksDaoTest() {
        bulkWriteTasks(false)
        val tasks = taskDao.getAllTasks()
        val deletedCount = taskDao.deleteAllTasks()

        val countsMatch = deletedCount == tasks.size
        val noMoreTasks = taskDao.getAllTasks().isEmpty()

        assert(countsMatch && noMoreTasks)
    }

    // helper function that can be called to add a task to the db without calling the full test
    private fun writeTask(withDates: Boolean): Map<String, Task> {
        bulkWriteProjects()
        dbSeeder.populateTasksList(withDates)

        val taskToInsert = dbSeeder.tasks[0]
        val returnedId = taskDao.insertTask(taskToInsert)
        val returnedTask = taskDao.getTaskById(returnedId)

        val compareValues = mutableMapOf<String, Task>()
        compareValues[KEY_INSERTED] = taskToInsert
        compareValues[KEY_RETURNED] = returnedTask
        return compareValues
    }

    // helper function that can be called to add a list of tasks to the db without calling the full test
    private fun bulkWriteTasks(withDates: Boolean): Map<String, Int> {
        bulkWriteProjects()
        dbSeeder.populateTasksList(withDates)

        val tasksToInsert = dbSeeder.tasks
        val returnedIds = taskDao.insertTasks(tasksToInsert)

        val compareValues = mutableMapOf<String, Int>()
        compareValues[KEY_INSERTED] = tasksToInsert.size
        compareValues[KEY_RETURNED] = returnedIds.size
        return compareValues
    }

    /**
     * TaskTagDao tests and helpers
     */
    @Test
    @Throws(Exception::class)
    fun writeTaskTagJoinDaoTest() {
        val compareValues = writeTaskTagJoin()
        assert(compareValues[KEY_INSERTED] == compareValues[KEY_RETURNED])
    }

    @Test
    @Throws(Exception::class)
    fun bulkWriteTaskTagJoinDaoTest() {
        val compareValues = bulkWriteTaskTagJoins()
        assert(compareValues[KEY_INSERTED] == compareValues[KEY_RETURNED])
    }

    @Test
    @Throws(Exception::class)
    fun updateTaskTagJoinDaoTest() {
        val taskTagJoin = writeTaskTagJoin()[KEY_RETURNED]

        if (taskTagJoin != null) {
            taskTagJoin.taskId = taskDao.getAllTasks()[1].taskId
            taskTagJoin.tagId = tagDao.getAllTags()[3].tagId
            val updatedCount = taskTagJoinDao.updateTaskTagJoin(taskTagJoin)
            assert(updatedCount == 1)
        } else {
            throw Exception("taskTagJoin is null")
        }
    }

    @Test
    @Throws(Exception::class)
    fun bulkUpdateTaskTagJoinDaoTest() {
        bulkWriteTaskTagJoins()

        val startingTaskTagJoins = taskTagJoinDao.getAllTaskTagJoins()
        startingTaskTagJoins.forEach { taskTagJoin ->
            taskTagJoin.taskId = taskDao.getAllTasks()[1].taskId
            taskTagJoin.tagId = tagDao.getAllTags()[3].tagId
        }

        val updatedCount = taskTagJoinDao.bulkUpdateTaskTagJoins(startingTaskTagJoins)
        assert(updatedCount == startingTaskTagJoins.size)
    }

    @Test
    @Throws(Exception::class)
    fun deleteTaskTagJoinDaoTest() {
        val taskTagJoin = writeTaskTagJoin()[KEY_RETURNED]
        if (taskTagJoin != null) {
            val deletedCount = taskTagJoinDao.deleteTaskTagJoin(taskTagJoin)
            assert(deletedCount == 1)
        } else {
            throw Exception("taskTagJoin is null")
        }
    }

    @Test
    @Throws(Exception::class)
    fun bulkDeleteTaskTagJoinsDaoTest() {
        bulkWriteTaskTagJoins()
        val taskTagJoins = taskTagJoinDao.getAllTaskTagJoins()
        val deletedCount = taskTagJoinDao.bulkDeleteTaskTagJoins(taskTagJoins)

        assert(taskTagJoins.size == deletedCount)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllTaskTagJoinsDaoTest() {
        bulkWriteTaskTagJoins()
        val taskTagJoins = taskTagJoinDao.getAllTaskTagJoins()
        val deletedCount = taskTagJoinDao.deleteAllTaskTagJoins()

        val countsMatch = deletedCount == taskTagJoins.size
        val noMoreTaskTagJoins = taskTagJoinDao.getAllTaskTagJoins().isEmpty()

        assert(countsMatch && noMoreTaskTagJoins)
    }

    // helper function that can be called to add a taskTagJoin to the db without calling the full test
    private fun writeTaskTagJoin(): Map<String, TaskTagJoin> {
        bulkWriteTasks(false)
        bulkWriteTags()
        dbSeeder.populateTaskTagJoinList()

        val taskTagJoinToInsert = dbSeeder.taskTagJoins[0]
        val returnedId = taskTagJoinDao.insertTaskTagJoin(taskTagJoinToInsert)
        val returnedTaskTagJoin = taskTagJoinDao.getTaskTagJoinById(returnedId)

        val compareValues = mutableMapOf<String, TaskTagJoin>()
        compareValues[KEY_INSERTED] = taskTagJoinToInsert
        compareValues[KEY_RETURNED] = returnedTaskTagJoin
        return compareValues
    }

    // helper function that can be called to add a list of taskTagJoins to the db without calling the full test
    private fun bulkWriteTaskTagJoins(): Map<String, Int> {
        bulkWriteTasks(false)
        bulkWriteTags()
        dbSeeder.populateTaskTagJoinList()

        val taskTagJoinsToInsert = dbSeeder.taskTagJoins
        val returnedIds = taskTagJoinDao.insertTaskTagJoins(taskTagJoinsToInsert)

        val compareValues = mutableMapOf<String, Int>()
        compareValues[KEY_INSERTED] = taskTagJoinsToInsert.size
        compareValues[KEY_RETURNED] = returnedIds.size
        return compareValues
    }

}