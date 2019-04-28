package com.awesome.zach.jotunheimrsandbox.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.awesome.zach.jotunheimrsandbox.data.entities.JHList

@Dao
interface ListDao {

    @Query("SELECT count(id) FROM list")
    fun count(): Int

    @Query("SELECT count(id) FROM list")
    fun has(): Boolean

    @Query("SELECT * FROM list WHERE id = :id")
    fun listByIdNow(id: Long): JHList?

    @Query("SELECT * FROM list WHERE id = :id")
    fun listById(id: Long): LiveData<JHList?>

    @Query("SELECT * FROM list ORDER BY name ASC")
    fun list(): LiveData<List<JHList>>

    @Query("SELECT * FROM list ORDER BY name ASC")
    fun listNow(): List<JHList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg list: JHList): Array<Long>

    @Query("DELETE FROM list WHERE id = :id")
    fun deleteById(id: Long): Long

    @Delete
    fun delete(list: JHList): Long

    @Query("DELETE FROM list")
    fun deleteAll(): List<Long>
    
    /** Old methods below */
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
    @Query("DELETE FROM list")
    fun deleteAllLists(): Int

    @Query("SELECT * FROM list ORDER BY name ASC")
    fun getAllListsLive(): LiveData<List<JHList>>

    @Query("SELECT * FROM list ORDER BY name ASC")
    fun getAllLists(): List<JHList>

    @Query("SELECT * FROM list WHERE id == :id ORDER BY name ASC")
    fun getListByID(id: Long): JHList

    @Query("SELECT * FROM list WHERE name == :name ORDER BY name ASC")
    fun getListByName(name: String): List<JHList>
}