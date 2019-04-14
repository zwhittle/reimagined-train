package com.awesome.zach.jotunheimrsandbox.data.entities

import androidx.annotation.NonNull
import androidx.room.*

/**
 * Tag @Entity
 * Tags are user generated, but a few will be preloaded
 * Tags can be assigned to Tasks for now, maybe Projects later
 *
 * Columns: tagId(PK), name, colorId(FK)
 * Null allowed: tagId(PK), isSelected
 * ForeignKeys: colorId (Color: colorId)
 *
 */

@Entity(tableName = "tag_table",
        indices = [Index("tagId"), Index("colorId")],
        foreignKeys = [ForeignKey(entity = Color::class,
                                  parentColumns = ["colorId"],
                                  childColumns = ["colorId"])])
data class Tag(
    @PrimaryKey(autoGenerate = true)
    var tagId: Long = 0,
    @NonNull
    var name: String,
    @NonNull
    var colorId: Long,
    @ColumnInfo(name = "hex")
    var colorHex: String? = null,
    var isSelected: Boolean = false)