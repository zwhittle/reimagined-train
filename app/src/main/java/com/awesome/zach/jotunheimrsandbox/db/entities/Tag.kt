package com.awesome.zach.jotunheimrsandbox.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * Tag @Entity
 * Tags are user generated, but a few will be preloaded
 * Tags can be assigned to Tasks for now, maybe Projects later
 *
 * Columns: tagId(PK), name, colorId(FK)
 * Null allowed: tagId(PK)
 * ForeignKeys: colorId (Color: colorId)
 *
 */

@Entity(tableName = "tag_table",
        indices = [Index("tagId")],
        foreignKeys = [ForeignKey(entity = Color::class,
                                  parentColumns = ["colorId"],
                                  childColumns = ["colorId"])])
data class Tag(
    @PrimaryKey(autoGenerate = true)
    var tagId: Long = 0,
    @NonNull
    var name: String,
    @NonNull
    var colorId: Long)