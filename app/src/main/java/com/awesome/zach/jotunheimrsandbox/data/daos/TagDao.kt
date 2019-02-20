package com.awesome.zach.jotunheimrsandbox.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.awesome.zach.jotunheimrsandbox.data.entities.Tag

/**
 * Tag @Dao
 *
 * Uses @Insert, @Update, and @Delete convenience functions
 *
 * Single Result Queries:
 * getTagById()
 *
 * Multi Result Queries:
 * getAllTags()
 * getTagsWithColor()
 * getTagsWithName()
 *
 */

@Dao
interface TagDao {

    // Returns the inserted row id
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTag(tag: Tag): Long

    // returns a list of inserted row ids
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsertTags(tags: List<Tag>): List<Long>

    // returns the count of updated rows
    @Update
    fun updateTag(tag: Tag): Int

    // returns the count of updated rows
    @Update
    fun bulkUpdateTags(tags: List<Tag>): Int

    // returns the count of deleted rows
    @Delete
    fun deleteTag(tag: Tag): Int

    // returns the count of deleted rows
    @Delete
    fun bulkDeleteTags(tags: List<Tag>): Int

    // returns the count of deleted rows
    @Query("DELETE FROM tag_table")
    fun deleteAllTags(): Int

    @Query("SELECT tag_table.tagId, tag_table.name, tag_table.colorId, color_table.hex FROM tag_table INNER JOIN color_table ON tag_table.colorId = color_table.colorId ORDER BY tag_table.tagId ASC")
    fun getAllTags(): List<Tag>

    @Query("SELECT tag_table.tagId, tag_table.name, tag_table.colorId, color_table.hex FROM tag_table INNER JOIN color_table ON tag_table.colorId = color_table.colorId ORDER BY tag_table.tagId ASC")
    fun getAllTagsLive(): LiveData<List<Tag>>

    @Query("SELECT * FROM tag_table WHERE tagId == :tagId")
    fun getTagById(tagId: Long): Tag

    @Query("SELECT * FROM tag_table WHERE name == :name ORDER BY tagId ASC")
    fun getTagsWithName(name: String): List<Tag>

    @Query("SELECT * FROM tag_table WHERE colorId == :colorId ORDER BY tagId ASC")
    fun getTagsWithColor(colorId: Long): List<Tag>
}