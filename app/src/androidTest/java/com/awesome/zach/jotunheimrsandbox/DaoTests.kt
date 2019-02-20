package com.awesome.zach.jotunheimrsandbox

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.awesome.zach.jotunheimrsandbox.data.AppDatabase
import com.awesome.zach.jotunheimrsandbox.data.DBSeeder
import com.awesome.zach.jotunheimrsandbox.data.daos.*
import com.awesome.zach.jotunheimrsandbox.data.entities.*
import com.awesome.zach.jotunheimrsandbox.utils.Utils
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.time.LocalDate

class DaoTests {
    private lateinit var colorDao: ColorDao
    private lateinit var tagDao: TagDao
    private lateinit var projectDao: ProjectDao
    private lateinit var taskDao: TaskDao
    private lateinit var taskTagAssignmentDao: TaskTagAssignmentDao
    private lateinit var db: AppDatabase

    private lateinit var dbSeeder: DBSeeder

    companion object {
        const val KEY_INSERTED = "key_inserted"
        const val KEY_RETURNED = "key_returned"
    }

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
//        db = AppDatabase.getDatabase(context)

        colorDao = db.colorDao()
        tagDao = db.tagDao()
        projectDao = db.projectDao()
        taskDao = db.taskDao()
        taskTagAssignmentDao = db.taskTagAssignmentDao()

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
        assertThat(compareValues[KEY_INSERTED], equalTo(compareValues[KEY_RETURNED]))
    }

    // inserts a list of colors into the DB and compares the count of before to the count after
    @Test
    @Throws(Exception::class)
    fun bulkWriteColorsDaoTest() {
        val compareValues = bulkWriteColors()
        assertThat(compareValues[KEY_INSERTED], equalTo(compareValues[KEY_RETURNED]))
    }

    @Test
    @Throws(Exception::class)
    fun updateColorDaoTest() {
        val color = writeColor()[KEY_RETURNED]
        if (color != null) {
            color.name = "Lol Pink Or Whatever"
            color.hex = "#696969"
            val updatedCount = colorDao.updateColor(color)
            assertThat(updatedCount, equalTo(1))
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
        assertThat(updatedCount, equalTo(colors.size))
    }

    @Test
    @Throws(Exception::class)
    fun deleteColorDaoTest() {
        val color = writeColor()[KEY_RETURNED]
        if (color != null) {
            val deletedCount = colorDao.deleteColor(color)
            assertThat(deletedCount, equalTo(1))
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

        assertThat(colors.size, equalTo(deletedCount))
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllColorsDaoTest() {
        bulkWriteColors()
        val colors = colorDao.getAllColors()
        val deletedCount = colorDao.deleteAllColors()

        val countsMatch = deletedCount == colors.size
        val noMoreColors = colorDao.getAllColors().isEmpty()

        assertThat(noMoreColors, equalTo(true))
    }

    // helper function that can be called to add a color to the db without calling the full test
    private fun writeColor(): Map<String, Color> {
        dbSeeder.populateColorsList()
        val colorToInsert: Color = dbSeeder.colors[0]
        val returnedId = colorDao.insertColor(colorToInsert)
        val returnedColor = colorDao.getColorById(returnedId)
        colorToInsert.colorId = returnedId

        val compareValues = mutableMapOf<String, Color>()
        compareValues[KEY_INSERTED] = colorToInsert
        compareValues[KEY_RETURNED] = returnedColor
        return compareValues
    }

    // helper function that can be called to add a list of colors to the db without calling the full test
    private fun bulkWriteColors(): Map<String, Int> {
        dbSeeder.populateColorsList()
        val colorsToInsert = dbSeeder.colors
        val returnedIds = colorDao.bulkInsertColors(colorsToInsert)
        dbSeeder.colors = ArrayList(colorDao.getAllColors())

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
        assertThat(compareValues[KEY_INSERTED], equalTo(compareValues[KEY_RETURNED]))
    }

    @Test
    @Throws(Exception::class)
    fun bulkWriteTagsDaoTest() {
        val compareValues = bulkWriteTags()
        assertThat(compareValues[KEY_INSERTED], equalTo(compareValues[KEY_RETURNED]))
    }

    @Test
    @Throws(Exception::class)
    fun updateTagDaoTest() {
        val tag = writeTag()[KEY_RETURNED]

        if (tag != null) {
            tag.name = "Nice"
            tag.colorId = colorDao.getColorByHex("#696969").colorId
            val updatedCount = tagDao.updateTag(tag)
            assertThat(updatedCount, equalTo(1))
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
        assertThat(updatedCount, equalTo(tags.size))
    }

    @Test
    @Throws(Exception::class)
    fun deleteTagDaoTest() {
        val tag = writeTag()[KEY_RETURNED]
        if (tag != null) {
            val deletedCount = tagDao.deleteTag(tag)
            assertThat(deletedCount, equalTo(1))
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

        assertThat(tags.size, equalTo(deletedCount))
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllTagsDaoTest() {
        bulkWriteTags()
        val tags = tagDao.getAllTags()
        val deletedCount = tagDao.deleteAllTags()

        val countsMatch = deletedCount == tags.size
        val noMoreTags = tagDao.getAllTags().isEmpty()

        assertThat(noMoreTags, equalTo(true))
    }

    // helper function that can be called to add a tag to the db without calling the full test
    private fun writeTag(): Map<String, Tag> {
        bulkWriteColors()
        dbSeeder.populateTagsList()

        val tagToInsert = dbSeeder.tags[0]
        val returnedId = tagDao.insertTag(tagToInsert)
        val returnedTag = tagDao.getTagById(returnedId)
        tagToInsert.tagId = returnedId

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
        val returnedIds = tagDao.bulkInsertTags(tagsToInsert)
        dbSeeder.tags = ArrayList(tagDao.getAllTags())

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
        assertThat(compareValues[KEY_INSERTED], equalTo(compareValues[KEY_RETURNED]))
    }

    @Test
    @Throws(Exception::class)
    fun bulkWriteProjectsDaoTest() {
        val compareValues = bulkWriteProjects()
        assertThat(compareValues[KEY_INSERTED], equalTo(compareValues[KEY_RETURNED]))
    }

    @Test
    @Throws(Exception::class)
    fun updateProjectDaoTest() {
        val project = writeProject()[KEY_RETURNED]

        if (project != null) {
            project.name = "Nice"
            project.colorId = colorDao.getColorByHex("#696969").colorId
            val updatedCount = projectDao.updateProject(project)
            assertThat(updatedCount, equalTo(1))
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
        assertThat(updatedCount, equalTo(projects.size))
    }

    @Test
    @Throws(Exception::class)
    fun deleteProjectDaoTest() {
        val project = writeProject()[KEY_RETURNED]
        if (project != null) {
            val deletedCount = projectDao.deleteProject(project)
            assertThat(deletedCount, equalTo(1))
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

        assertThat(projects.size, equalTo(deletedCount))
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllProjectsDaoTest() {
        bulkWriteProjects()
        val projects = projectDao.getAllProjects()
        val deletedCount = projectDao.deleteAllProjects()

        val countsMatch = deletedCount == projects.size
        val noMoreProjects = projectDao.getAllProjects().isEmpty()

        assertThat(noMoreProjects, equalTo(true))
    }

    // helper function that can be called to add a project to the db without calling the full test
    private fun writeProject(): Map<String, Project> {
        bulkWriteColors()
        dbSeeder.populateProjectsList()

        val projectToInsert = dbSeeder.projects[0]
        val returnedId = projectDao.insertProject(projectToInsert)
        val returnedProject = projectDao.getProjectById(returnedId)
        projectToInsert.projectId = returnedId

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
        val returnedIds = projectDao.bulkInsertProjects(projectsToInsert)
        dbSeeder.projects = ArrayList(projectDao.getAllProjects())

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
        assertThat(compareValues[KEY_INSERTED], equalTo(compareValues[KEY_RETURNED]))
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun writeTaskWithDatesDaoTest() {
        val compareValues = writeTask(true)
        assertThat(compareValues[KEY_INSERTED], equalTo(compareValues[KEY_RETURNED]))
    }


    @Test
    @Throws(Exception::class)
    fun bulkWriteTasksWithoutDatesDaoTest() {
        val compareValues = bulkWriteTasks(false)
        assertThat(compareValues[KEY_INSERTED], equalTo(compareValues[KEY_RETURNED]))
    }

    @Test
    @Throws(Exception::class)
    fun bulkWriteTasksWithDatesDaoTest() {
        val compareValues = bulkWriteTasks(true)
        assertThat(compareValues[KEY_INSERTED], equalTo(compareValues[KEY_RETURNED]))
    }

    @Test
    @Throws(Exception::class)
    fun updateTaskDaoTest() {
        val task = writeTask(false)[KEY_RETURNED]

        if (task != null) {
            task.name = "Nice"
            task.date_start = LocalDate.now()
            task.date_end = LocalDate.now()
            task.projectId = projectDao.getProjectsByName("Project 1")[0].projectId
            val updatedCount = taskDao.updateTask(task)
            assertThat(updatedCount, equalTo(1))
        } else {
            throw Exception("task is null")
        }
    }

    @Test
    @Throws(Exception::class)
    fun bulkUpdateTaskDaoTest() {
        bulkWriteTasks(false)
        val tasks = taskDao.getAllTasks()
        tasks.forEach { task ->
            task.name = "Nice"
            task.date_start = LocalDate.now()
            task.date_end = LocalDate.now()
            task.projectId = projectDao.getProjectsByName("Project 1")[0].projectId
        }

        val updatedCount = taskDao.bulkUpdateTasks(tasks)
        assertThat(updatedCount, equalTo(tasks.size))
    }

    @Test
    @Throws(Exception::class)
    fun deleteTaskDaoTest() {
        val task = writeTask(false)[KEY_RETURNED]
        if (task != null) {
            val deletedCount = taskDao.deleteTask(task)
            assertThat(deletedCount, equalTo(1))
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

        assertThat(tasks.size, equalTo(deletedCount))
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllTasksDaoTest() {
        bulkWriteTasks(false)
        val tasks = taskDao.getAllTasks()
        val deletedCount = taskDao.deleteAllTasks()

        val countsMatch = deletedCount == tasks.size
        val noMoreTasks = taskDao.getAllTasks().isEmpty()

        assertThat(noMoreTasks, equalTo(true))
    }

    @Test
    @Throws(Exception::class)
    fun getTasksInRangeDaoTest() {
        bulkWriteTasks(true)
        val first = Utils.firstDayOfThisWeek()
        val last = Utils.lastDayOfThisWeek()
        val tasks = taskDao.getTasksDueInRange(first, last)
        assertThat(tasks.size, equalTo(4))
    }

    // helper function that can be called to add a task to the db without calling the full test
    private fun writeTask(withDates: Boolean): Map<String, Task> {
        bulkWriteProjects()
        dbSeeder.populateTasksList(withDates)

        val taskToInsert = dbSeeder.tasks[0]
        val returnedId = taskDao.insertTask(taskToInsert)
        val returnedTask = taskDao.getTaskById(returnedId)
        taskToInsert.taskId = returnedId

        val compareValues = mutableMapOf<String, Task>()
        compareValues[KEY_INSERTED] = taskToInsert
        compareValues[KEY_RETURNED] = returnedTask
        return compareValues
    }

    // helper function that can be called to add a list of tasks to the db without calling the full test
    private fun bulkWriteTasks(withDates: Boolean): Map<String, Int> {
        bulkWriteProjects()
        dbSeeder.populateTasksList(withDates)

        var tasksToInsert = dbSeeder.tasks
        val returnedIds = taskDao.bulkInsertTasks(tasksToInsert)
        dbSeeder.tasks = ArrayList(taskDao.getAllTasks())

        tasksToInsert = dbSeeder.tasks

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
    fun writeTaskTagAssignmentDaoTest() {
        val compareValues = writeTaskTagAssignment()
        assertThat(compareValues[KEY_INSERTED], equalTo(compareValues[KEY_RETURNED]))
    }

    @Test
    @Throws(Exception::class)
    fun bulkWriteTaskTagAssignmentDaoTest() {
        val compareValues = bulkWriteTaskTagAssignments()
        assertThat(compareValues[KEY_INSERTED], equalTo(compareValues[KEY_RETURNED]))
    }

    @Test
    @Throws(Exception::class)
    fun updateTaskTagAssignmentDaoTest() {
        val taskTagAssignment = writeTaskTagAssignment()[KEY_RETURNED]

        if (taskTagAssignment != null) {
            taskTagAssignment.taskId = taskDao.getAllTasks()[1].taskId
            taskTagAssignment.tagId = tagDao.getAllTags()[3].tagId
            val updatedCount = taskTagAssignmentDao.updateTaskTagAssignment(taskTagAssignment)
            assertThat(updatedCount, equalTo(1))
        } else {
            throw Exception("taskTagAssignment is null")
        }
    }

    @Test
    @Throws(Exception::class)
    fun bulkUpdateTaskTagAssignmentDaoTest() {
        bulkWriteTaskTagAssignments()

        val taskTagAssignments = taskTagAssignmentDao.getAllTaskTagAssignments()
        taskTagAssignments.forEach { taskTagAssignment ->
            taskTagAssignment.taskId = taskDao.getAllTasks()[1].taskId
            taskTagAssignment.tagId = tagDao.getAllTags()[3].tagId
        }

        val updatedCount = taskTagAssignmentDao.bulkUpdateTaskTagAssignments(taskTagAssignments)
        assertThat(updatedCount, equalTo(taskTagAssignments.size))
    }

    @Test
    @Throws(Exception::class)
    fun deleteTaskTagAssignmentDaoTest() {
        val taskTagAssignment = writeTaskTagAssignment()[KEY_RETURNED]
        if (taskTagAssignment != null) {
            val deletedCount = taskTagAssignmentDao.deleteTaskTagAssignment(taskTagAssignment)
            assertThat(deletedCount, equalTo(1))
        } else {
            throw Exception("taskTagAssignment is null")
        }
    }

    @Test
    @Throws(Exception::class)
    fun bulkDeleteTaskTagAssignmentsDaoTest() {
        bulkWriteTaskTagAssignments()
        val taskTagAssignments = taskTagAssignmentDao.getAllTaskTagAssignments()
        val deletedCount = taskTagAssignmentDao.bulkDeleteTaskTagAssignments(taskTagAssignments)

        assertThat(taskTagAssignments.size, equalTo(deletedCount))
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllTaskTagAssignmentsDaoTest() {
        bulkWriteTaskTagAssignments()
        val taskTagAssignments = taskTagAssignmentDao.getAllTaskTagAssignments()
        val deletedCount = taskTagAssignmentDao.deleteAllTaskTagAssignments()

        val countsMatch = deletedCount == taskTagAssignments.size
        val noMoreTaskTagAssignments = taskTagAssignmentDao.getAllTaskTagAssignments().isEmpty()

        assert(countsMatch && noMoreTaskTagAssignments)
    }

    // helper function that can be called to add a taskTagAssignment to the db without calling the full test
    private fun writeTaskTagAssignment(): Map<String, TaskTagAssignment> {
        bulkWriteTasks(false)
        bulkWriteTags()
        dbSeeder.populateTaskTagAssignmentsList()

        val taskTagAssignmentToInsert = dbSeeder.taskTagAssignments[0]
        val returnedId = taskTagAssignmentDao.insertTaskTagAssignment(taskTagAssignmentToInsert)
        val returnedTaskTagAssignment = taskTagAssignmentDao.getTaskTagAssignmentById(returnedId)
        taskTagAssignmentToInsert.taskTagAssignmentId = returnedId

        val compareValues = mutableMapOf<String, TaskTagAssignment>()
        compareValues[KEY_INSERTED] = taskTagAssignmentToInsert
        compareValues[KEY_RETURNED] = returnedTaskTagAssignment
        return compareValues
    }

    // helper function that can be called to add a list of taskTagAssignments to the db without calling the full test
    private fun bulkWriteTaskTagAssignments(): Map<String, Int> {
        bulkWriteTasks(false)
        bulkWriteTags()
        dbSeeder.populateTaskTagAssignmentsList()

        val taskTagAssignmentsToInsert = dbSeeder.taskTagAssignments
        val returnedIds = taskTagAssignmentDao.bulkInsertTaskTagAssignments(taskTagAssignmentsToInsert)
        dbSeeder.taskTagAssignments = ArrayList(taskTagAssignmentDao.getAllTaskTagAssignments())

        val compareValues = mutableMapOf<String, Int>()
        compareValues[KEY_INSERTED] = taskTagAssignmentsToInsert.size
        compareValues[KEY_RETURNED] = returnedIds.size
        return compareValues
    }

}