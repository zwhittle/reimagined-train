package com.awesome.zach.jotunheimrsandbox.data.entities

import androidx.room.*
import java.time.Instant
import java.time.LocalDate

@Entity(indices = [Index("id"), Index("projectId"), Index("listId")],
        foreignKeys = [
            ForeignKey(
                entity = Project::class,
                parentColumns = ["id"],
                childColumns = ["projectId"]),
            ForeignKey(
                entity = JHList::class,
                parentColumns = ["id"],
                childColumns = ["listId"])])
data class Task(
    var name: String,
    var date_start: LocalDate? = null,
    var date_end: LocalDate? = null,
    var completed: Boolean = false,
    var priority: Int = 1,
    var projectId: Long? = null,
    var listId: Long? = null,
    var createdAt: Instant = Instant.now()) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Ignore
    var tags = ArrayList<Tag>()

    @Ignore
    var isSelected: Boolean = false

    @Ignore
    var projectName: String? = null

    @Ignore
    var listName: String? = null
}