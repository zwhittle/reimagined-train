package com.awesome.zach.jotunheimrsandbox.data.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "list")
data class JHList(
    @NonNull
    var name: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Ignore
    var isSelected: Boolean = false
}