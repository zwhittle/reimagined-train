package com.awesome.zach.jotunheimrsandbox.db.daos

import androidx.room.*
import com.awesome.zach.jotunheimrsandbox.db.entities.Tag

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
 * getTagsByColor()
 * getTagsByName()
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

    @Query("SELECT * FROM tag_table")
    fun getAllTags(): List<Tag>

    @Query("SELECT * FROM tag_table WHERE tagId == :tagId")
    fun getTagById(tagId: Long): Tag

    @Query("SELECT * FROM tag_table WHERE name == :name")
    fun getTagsByName(name: String): List<Tag>

    @Query("SELECT * FROM tag_table WHERE colorId == :colorId")
    fun getTagsByColor(colorId: Long): List<Tag>
}