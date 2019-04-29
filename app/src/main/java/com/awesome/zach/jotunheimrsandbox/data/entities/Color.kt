package com.awesome.zach.jotunheimrsandbox.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "color",
        indices = [Index("id"), Index("hex")])
data class Color(
    var name: String? = null,
    var hex: String = "#696969") {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}