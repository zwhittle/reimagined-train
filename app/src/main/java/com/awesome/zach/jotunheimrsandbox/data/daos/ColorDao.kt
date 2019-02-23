package com.awesome.zach.jotunheimrsandbox.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.awesome.zach.jotunheimrsandbox.data.entities.Color

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

    // returns the inserted row id
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertColor(color: Color) : Long

    // returns a list of inserted row ids
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsertColors(colors: List<Color>) : List<Long>

    // returns the count of updated rows
    @Update
    fun updateColor(color: Color) : Int

    // returns the count of updated rows
    @Update
    fun bulkUpdateColors(colors: List<Color>) : Int

    // returns the count of deleted rows
    @Delete
    fun deleteColor(color: Color) : Int

    // returns the count of deleted rows
    @Delete
    fun bulkDeleteColors(colors: List<Color>) : Int

    // returns the count of deleted rows
    @Query("DELETE FROM color_table")
    fun deleteAllColors() : Int

    @Query("SELECT * FROM color_table ORDER BY colorId ASC")
    fun getAllColorsLive() : LiveData<List<Color>>

    @Query("SELECT * FROM color_table ORDER BY colorId ASC")
    fun getAllColors() : List<Color>

    @Query("SELECT * FROM color_table WHERE colorId == :colorId ORDER BY colorId ASC")
    fun getColorById(colorId: Long) : Color

    @Query("SELECT * FROM color_table WHERE name == :name ORDER BY colorId ASC")
    fun getColorByName(name: String) : Color

    @Query("SELECT * FROM color_table WHERE hex == :hex ORDER BY colorId ASC")
    fun getColorByHex(hex: String) : Color
}