package com.awesome.zach.jotunheimrsandbox.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * Color @Entity
 * Colors are preloaded into the app, not user generated.
 * Colors can be assigned to Tags and Projects
 *
 * Columns: colorId(PK), name, hex
 * Null Allowed: colorId(PK), name (because I'm lazy and may not want to give them all names)
 *
 */

@Entity(tableName = "color_table",
        indices = [Index("colorId"), Index("hex")])
data class Color(
    @PrimaryKey(autoGenerate = true)
    var colorId: Long = 0, var name: String? = null,
    @NonNull
    var hex: String)