package com.awesome.zach.jotunheimrsandbox.db.daos

import android.arch.persistence.room.*
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

    // Returns the ID of the inserted row
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTag(tag: Tag) : Long

    // Returns the number of updated rows
    @Update
    fun updateTag(tag: Tag) : Int

    @Delete
    fun deleteTag(tag: Tag)

    @Query("SELECT * FROM tag_table")
    fun getAllTags() : List<Tag>

    @Query("SELECT * FROM tag_table WHERE tagId == :tagId")
    fun getTagById(tagId: Long): Tag

    @Query("SELECT * FROM tag_table WHERE name == :name")
    fun getTagsByName(name: String): List<Tag>

    @Query("SELECT * FROM tag_table WHERE colorId == :colorId")
    fun getTagsByColor(colorId: Long): List<Tag>
}