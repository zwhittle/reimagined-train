package com.awesome.zach.jotunheimrsandbox.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity
data class Project(
    var name: String,
    var createdAt: Instant = Instant.now()) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}