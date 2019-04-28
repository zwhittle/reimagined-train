package com.awesome.zach.jotunheimrsandbox.data.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * JHList @Entity
 * Lists are user generated, but a few will be preloaded
 * Lists can be assigned to only Tasks for now
 *
 * Columns: id(PK), name
 * Null allowed: isSelected(PK)
 *
 */

@Entity(tableName = "list")
data class JHList(
    @NonNull
    var name: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Ignore
    var isSelected: Boolean = false
}