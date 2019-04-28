package com.awesome.zach.jotunheimrsandbox.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Color @Entity
 * Colors are preloaded into the app, not user generated.
 * Colors can be assigned to Tags and Projects
 *
 * Columns: id(PK), name, hex
 * Null Allowed: id(PK), name (because I'm lazy and may not want to give them all names)
 *
 */

@Entity(tableName = "color",
        indices = [Index("id"), Index("hex")])
data class Color(
    var name: String? = null,
    var hex: String = "#696969") {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}