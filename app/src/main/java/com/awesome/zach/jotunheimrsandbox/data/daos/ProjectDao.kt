package com.awesome.zach.jotunheimrsandbox.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.awesome.zach.jotunheimrsandbox.data.entities.Project

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
interface ProjectDao {

    // returns the id of the inserted row
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProject(project: Project): Long

    // returns a list of inserted row ids
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsertProjects(projects: List<Project>): List<Long>

    // returns a count of updated rows
    @Update
    fun updateProject(project: Project): Int

    // returns a count of updated rows
    @Update
    fun bulkUpdateProjects(projects: List<Project>): Int

    // returns a count of deleted rows
    @Delete
    fun deleteProject(project: Project): Int

    // returns a count of deleted rows
    @Delete
    fun bulkDeleteProjects(projects: List<Project>): Int

    // returns a count of deleted rows
    @Query("DELETE FROM project_table")
    fun deleteAllProjects(): Int

    @Query("SELECT * FROM project_table INNER JOIN color_table ON project_table.colorId = color_table.colorId ORDER BY project_table.name ASC")
    fun getAllProjects(): List<Project>

    @Query("SELECT * FROM project_table INNER JOIN color_table ON project_table.colorId = color_table.colorId ORDER BY project_table.name ASC")
    fun getAllProjectsLive(): LiveData<List<Project>>

    @Query("SELECT * FROM project_table WHERE projectId == :projectId ORDER BY name ASC")
    fun getProjectById(projectId: Long): Project

    @Query("SELECT * FROM project_table WHERE name == :name ORDER BY name ASC")
    fun getProjectsByName(name: String): List<Project>

    @Query("SELECT * FROM project_table WHERE colorId == :colorId ORDER BY name ASC")
    fun getProjectsByColor(colorId: Long): List<Project>
}