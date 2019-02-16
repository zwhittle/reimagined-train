package com.awesome.zach.jotunheimrsandbox.utils

import com.awesome.zach.jotunheimrsandbox.db.entities.*
import java.util.*

object TestUtil {

    fun createColor(name: String, hex: String) = Color(
        colorId = null,
        name = name,
        hex = hex)

    fun createTag(name: String, colorId: Long) = Tag(
        tagId = null,
        name = name,
        colorId = colorId)

    fun createProject(name: String, colorId: Long) = Project(
        projectId = null,
        name = name,
        colorId = colorId)

    fun createTask(name: String, startDate: Date?, endDate: Date?, projectId: Long) = Task(
        taskId = null,
        name = name,
        date_start = startDate,
        date_end = endDate,
        projectId = projectId)

    fun createTaskTagJoin(taskId: Long, tagId: Long) = TaskTagJoin(
        taskId = taskId,
        tagId = tagId)
}