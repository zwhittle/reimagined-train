package com.awesome.zach.jotunheimrsandbox.data.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * JHList @Entity
 * Lists are user generated, but a few will be preloaded
 * Lists can be assigned to only Tasks for now
 *
 * Columns: listId(PK), name
 * Null allowed: isSelected(PK)
 *
 */

@Entity(tableName = "list_table")
data class JHList(
    @PrimaryKey(autoGenerate = true)
    var listId: Long = 0,
    @NonNull
    var listName: String,
    var isSelected: Boolean = false)