package com.awesome.zach.jotunheimrsandbox.data.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

/**
 * Tag @Entity
 * Tags are user generated, but a few will be preloaded
 * Tags can be assigned to Tasks for now, maybe Projects later
 *
 * Columns: id(PK), name, id(FK)
 * Null allowed: id(PK), isSelected
 * ForeignKeys: id (Color: id)
 *
 */

@Entity(tableName = "tag",
        indices = [Index("id"), Index("colorId")])
data class Tag(
    var name: String,
    var createdAt: Instant = Instant.now()) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    @Ignore
    var isSelected: Boolean = false
}