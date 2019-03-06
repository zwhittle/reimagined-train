package com.awesome.zach.jotunheimrsandbox.data.entities

import androidx.annotation.NonNull
import androidx.room.*

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

@Entity(tableName = "project_table",
        indices = [Index("projectId"), Index("colorId"), Index("hex")],
        foreignKeys = [ForeignKey(entity = Color::class,
                                  parentColumns = ["colorId"],
                                  childColumns = ["colorId"])])
data class Project(@PrimaryKey(autoGenerate = true)
                   var projectId: Int = 0,
                   @NonNull
                   @ColumnInfo(name = "projectName")
                   var name: String,
                   @NonNull
                   var colorId: Long,
                   @ColumnInfo(name = "hex")
                   var colorHex: String? = null)