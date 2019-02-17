package com.awesome.zach.jotunheimrsandbox.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * Project @Entity
 * Projects are user generated, but "inbox" will be preloaded
 * Projects can be assigned to Tasks
 *
 * Columns: projectId(PK), name, colorId (FK)
 * Null allowed: projectId(PK)
 * ForeignKeys: colorId (Color: colorId)
 *
 */

@Entity(
    tableName = "project_table",
    indices = [Index("projectId")],
    foreignKeys = [ForeignKey(
        entity = Color::class,
        parentColumns = ["colorId"],
        childColumns = ["colorId"]
    )]
)
data class Project(
    @PrimaryKey(autoGenerate = true)
    val projectId: Long = 0,
    @NonNull
    var name: String,
    @NonNull
    var colorId: Long
)