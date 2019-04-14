package com.awesome.zach.jotunheimrsandbox.utils

import com.awesome.zach.jotunheimrsandbox.data.entities.*
import java.time.LocalDate

object TestUtil {

    fun createColor(name: String, hex: String) = Color(
        name = name,
        hex = hex)

    fun createTag(name: String, colorId: Long) = Tag(
        name = name,
        colorId = colorId)

    fun createProject(name: String, colorId: Long) = Project(
        name = name,
        colorId = colorId)

    fun createTask(name: String, startDate: LocalDate?, endDate: LocalDate?, projectId: Long?) = Task(
        taskName = name,
        date_start = startDate,
        date_end = endDate,
        projectId = projectId)

    fun createTaskTagJoin(taskId: Long, tagId: Long) = TaskTagAssignment(
        taskId = taskId,
        tagId = tagId)
}