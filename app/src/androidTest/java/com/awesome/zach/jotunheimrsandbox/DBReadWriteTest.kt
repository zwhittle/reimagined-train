package com.awesome.zach.jotunheimrsandbox

import android.arch.persistence.room.Room
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.awesome.zach.jotunheimrsandbox.db.AppDatabase
import com.awesome.zach.jotunheimrsandbox.db.daos.*
import com.awesome.zach.jotunheimrsandbox.db.entities.*
import com.awesome.zach.jotunheimrsandbox.utils.TestUtil
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class DBReadWriteTest {
    private lateinit var colorDao: ColorDao
    private lateinit var tagDao: TagDao
    private lateinit var projectDao: ProjectDao
    private lateinit var taskDao: TaskDao
    private lateinit var taskTagJoinDao: TaskTagJoinDao
    private lateinit var db: AppDatabase

    private lateinit var insertedColor: Color
    private lateinit var insertedTag: Tag
    private lateinit var insertedProject: Project
    private lateinit var insertedTask: Task
    private lateinit var insertedTaskTagJoin: TaskTagJoin

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        colorDao = db.colorDao()
        tagDao = db.tagDao()
        projectDao = db.projectDao()
        taskDao = db.taskDao()
        taskTagJoinDao = db.taskTagJoinDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeColorAndReadInList() {
        val color: Color = TestUtil.createColor("Sky Blue", "#42b9f4")
        colorDao.insertColor(color)
        insertedColor = colorDao.getColorByHex(color.hex)
        assert(color == insertedColor)
    }

    @Test
    @Throws(Exception::class)
    fun writeTagAndReadInList() {
        writeColorAndReadInList()
        val tag: Tag = TestUtil.createTag("Home", insertedColor.colorId!!)
        val tagId = tagDao.insertTag(tag)
        insertedTag = tagDao.getTagById(tagId)
        assert(tag == insertedTag)
    }

    @Test
    @Throws(Exception::class)
    fun writeProjectAndReadInList() {
        writeColorAndReadInList()
        val project: Project = TestUtil.createProject("Inbox", insertedColor.colorId!!)
        val projectId = projectDao.insertProject(project)
        insertedProject = projectDao.getProjectById(projectId)
        assert(project == insertedProject)
    }

    @Test
    @Throws(Exception::class)
    fun writeTaskWithoutDatesAndReadInList() {
        writeProjectAndReadInList()
        val task: Task = TestUtil.createTask("Finish writing db unit tests", null, null, insertedProject.projectId!!)
        val taskId = taskDao.insertTask(task)
        insertedTask = taskDao.getTaskById(taskId)
        assert(task == insertedTask)
    }

    @Test
    @Throws(Exception::class)
    fun writeTaskWithDatesAndReadInList() {
        writeProjectAndReadInList()
        val task: Task = TestUtil.createTask("Finish writing db unit tests", Date(), Date(), insertedProject.projectId!!)
        val taskId = taskDao.insertTask(task)
        insertedTask = taskDao.getTaskById(taskId)
        assert(task == insertedTask)
    }

    @Test
    @Throws(Exception::class)
    fun writeTaskTagJoinAndReadInList() {
        writeTagAndReadInList()
        writeTagAndReadInList()
        writeTaskWithoutDatesAndReadInList()
        writeTaskWithDatesAndReadInList()
        val taskTagJoin: TaskTagJoin = TestUtil.createTaskTagJoin(taskId = insertedTask.taskId!!, tagId = insertedTag.tagId!!)
        val taskTagJoinId = taskTagJoinDao.insertTaskTagJoin(taskTagJoin)
        insertedTaskTagJoin = taskTagJoinDao.getTaskTagJoinById(taskTagJoinId)
        assert(taskTagJoin == insertedTaskTagJoin)
    }

}