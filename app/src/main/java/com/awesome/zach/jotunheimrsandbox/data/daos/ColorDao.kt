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
 * colorByIdNow()
 * colorByName()
 * colorByHexNow()
 *
 * Multi Result Queries:
 * listNow()
 *
 */

@Dao
interface ColorDao {

    @Query("SELECT count(id) FROM color")
    fun count(): Int

    @Query("SELECT count(id) FROM color")
    fun has(): Boolean

    @Query("SELECT * FROM color WHERE id == :colorId")
    fun colorByIdNow(colorId: Long): Color?

    @Query("SELECT * FROM color WHERE id == :colorId")
    fun colorById(colorId: Long): LiveData<Color?>

    @Query("SELECT * FROM color ORDER BY id ASC")
    fun list(): LiveData<List<Color>>

    @Query("SELECT * FROM color ORDER BY id ASC")
    fun listNow(): List<Color>

    @Query("SELECT * FROM color WHERE hex == :hex ORDER BY name ASC")
    fun colorByHexNow(hex: String): Color?

    @Query("SELECT * FROM color WHERE hex == :hex ORDER BY name ASC")
    fun colorByHex(hex: String): LiveData<Color?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg color: Color): Array<Long>

    @Query("DELETE FROM color WHERE id = :id")
    fun deleteById(id: Long): Int

    @Delete
    fun delete(color: Color): Int

    @Query("DELETE FROM color")
    fun deleteAll(): Int

    /** Old methods below */
    // returns the inserted row id
    // @Insert(onConflict = OnConflictStrategy.REPLACE)
    // fun insertColor(color: Color): Long
    //
    // // returns a list of inserted row ids
    // @Insert(onConflict = OnConflictStrategy.REPLACE)
    // fun bulkInsertColors(colors: List<Color>): List<Long>
    //
    // // returns the count of updated rows
    // @Update
    // fun updateColor(color: Color): Int
    //
    // // returns the count of updated rows
    // @Update
    // fun bulkUpdateColors(colors: List<Color>): Int
    //
    // // returns the count of deleted rows
    // @Delete
    // fun deleteColor(color: Color): Int
    //
    // // returns the count of deleted rows
    // @Delete
    // fun bulkDeleteColors(colors: List<Color>): Int
    //
    // // returns the count of deleted rows
    // @Query("DELETE FROM color")
    // fun deleteAllColors(): Int
}