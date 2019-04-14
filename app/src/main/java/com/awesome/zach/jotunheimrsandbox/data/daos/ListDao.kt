package com.awesome.zach.jotunheimrsandbox.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.awesome.zach.jotunheimrsandbox.data.entities.JHList

/**
 * Project @Dao
 *
 * Uses @Insert, @Update, and @Delete convenience functions
 *
 * Single Result Queries:
 * getProjectById()
 *
 * Multi Result Queries:
 * getAllProjects()
 * getProjectsByColor()
 * getProjectsByName()
 *
 */

@Dao
interface ListDao {

    // returns the id of the inserted row
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list: JHList): Long

    // returns a list of inserted row ids
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsertLists(lists: List<JHList>): List<Long>

    // returns a count of updated rows
    @Update
    fun updateList(list: JHList): Int

    // returns a count of updated rows
    @Update
    fun bulkUpdateLists(lists: List<JHList>): Int

    // returns a count of deleted rows
    @Delete
    fun deleteList(list: JHList): Int

    // returns a count of deleted rows
    @Delete
    fun bulkDeleteLists(lists: List<JHList>): Int

    // returns a count of deleted rows
    @Query("DELETE FROM list_table")
    fun deleteAllLists(): Int

    @Query("SELECT * FROM list_table ORDER BY listName ASC")
    fun getAllListsLive(): LiveData<List<JHList>>

    @Query("SELECT * FROM list_table ORDER BY listName ASC")
    fun getAllLists(): List<JHList>

    @Query("SELECT * FROM list_table WHERE listId == :listId ORDER BY listName ASC")
    fun getListByID(listId: Long): JHList

    @Query("SELECT * FROM list_table WHERE listName == :name ORDER BY listName ASC")
    fun getListByName(name: String): List<JHList>
}