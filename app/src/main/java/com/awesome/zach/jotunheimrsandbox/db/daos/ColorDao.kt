package com.awesome.zach.jotunheimrsandbox.db.daos

import android.arch.persistence.room.*
import com.awesome.zach.jotunheimrsandbox.db.entities.Color

/**
 * Color @Dao
 *
 * Uses @Insert, @Update, and @Delete convenience functions
 *
 * Single Result Queries:
 * getColorById()
 * getColorByName()
 * getColorByHex()
 *
 * Multi Result Queries:
 * getAllColors()
 *
 */

@Dao
interface ColorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertColor(color: Color)

    @Update
    fun updateColor(color: Color)

    @Delete
    fun deleteColor(color: Color)

    @Query("SELECT * FROM color_table")
    fun getAllColors() : List<Color>

    @Query("SELECT * FROM color_table WHERE colorId == :colorId")
    fun getColorById(colorId: Long): Color

    @Query("SELECT * FROM color_table WHERE name == :name")
    fun getColorByName(name: String): Color

    @Query("SELECT * FROM color_table WHERE hex == :hex")
    fun getColorByHex(hex: String): Color
}