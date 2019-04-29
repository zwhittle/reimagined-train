package com.awesome.zach.jotunheimrsandbox.data.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.Instant


@Entity
data class Tag(
    var name: String,
    var createdAt: Instant = Instant.now()) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    @Ignore
    var isSelected: Boolean = false
}