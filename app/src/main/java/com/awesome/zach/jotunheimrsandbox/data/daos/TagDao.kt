package com.awesome.zach.jotunheimrsandbox.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag


@Dao
interface TagDao {

    @Query("SELECT count(id) FROM tag")
    fun count(): Int

    @Query("SELECT count(id) FROM tag")
    fun has(): Boolean

    @Query("SELECT * FROM tag WHERE id = :id")
    fun tagByIdNow(id: Long): Tag?

    @Query("SELECT * FROM tag WHERE id = :id")
    fun tagById(id: Long): LiveData<Tag?>

    @Query("SELECT * FROM tag INNER JOIN task_tag ON tag.id = task_tag.taskId WHERE task_tag.taskId = :taskId")
    fun tagsByTask(taskId: Long): LiveData<List<Tag>>

    @Query("SELECT * FROM tag ORDER BY name DESC")
    fun list(): LiveData<List<Tag>>
    
    @Query("SELECT * FROM tag ORDER BY name DESC")
    fun listNow(): List<Tag>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tag: Tag): Array<Long>
    
    @Query("DELETE FROM tag WHERE id = :id")
    fun deleteById(id: Long): Int
    
    @Delete
    fun delete(tag: Tag): Int
    
    @Query("DELETE FROM tag")
    fun deleteAll(): Int
    
    /** Old methods below */
    // Returns the inserted row id
    // @Insert(onConflict = OnConflictStrategy.REPLACE)
    // fun insertTag(tag: Tag): Long
    //
    // // returns a list of inserted row ids
    // @Insert(onConflict = OnConflictStrategy.REPLACE)
    // fun bulkInsertTags(tags: List<Tag>): List<Long>
    //
    // // returns the count of updated rows
    // @Update
    // fun updateTag(tag: Tag): Int
    //
    // // returns the count of updated rows
    // @Update
    // fun bulkUpdateTags(tags: List<Tag>): Int
    //
    // // returns the count of deleted rows
    // @Delete
    // fun deleteTag(tag: Tag): Int
    //
    // // returns the count of deleted rows
    // @Delete
    // fun bulkDeleteTags(tags: List<Tag>): Int
    //
    // // returns the count of deleted rows
    // @Query("DELETE FROM tag")
    // fun deleteAllTags(): Int
    //
    // @Query("SELECT tag.id, tag.name, tag.id, color.hex FROM tag INNER JOIN color ON tag.id = color.id ORDER BY tag.id ASC")
    // fun getAllTags(): List<Tag>
    //
    // @Query("SELECT tag.id, tag.name, tag.id, color.hex FROM tag INNER JOIN color ON tag.id = color.id ORDER BY tag.id ASC")
    // fun getAllTagsLive(): LiveData<List<Tag>>
    //
    // @Query("SELECT * FROM tag WHERE id == :id")
    // fun getTagById(id: Long): Tag
    //
    // @Query("SELECT * FROM tag WHERE name == :name ORDER BY id ASC")
    // fun getTagsWithName(name: String): List<Tag>
    //
    // @Query("SELECT * FROM tag WHERE id == :id ORDER BY id ASC")
    // fun getTagsWithColor(id: Long): List<Tag>
}