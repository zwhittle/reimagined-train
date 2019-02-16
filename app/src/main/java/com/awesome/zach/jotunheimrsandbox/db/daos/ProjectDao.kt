package com.awesome.zach.jotunheimrsandbox.db.daos

import android.arch.persistence.room.*
import com.awesome.zach.jotunheimrsandbox.db.entities.Project

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
    fun insertProject(project: Project) : Long

    // returns a count of the number of updated rows
    @Update
    fun updateProject(project: Project) : Int

    @Delete
    fun deleteProject(project: Project)

    @Query("SELECT * FROM project_table")
    fun getAllProjects() : List<Project>

    @Query("SELECT * FROM project_table WHERE projectId == :projectId")
    fun getProjectById(projectId: Long): Project

    @Query("SELECT * FROM project_table WHERE name == :name")
    fun getProjectsByName(name: String): List<Project>

    @Query("SELECT * FROM project_table WHERE colorId == :colorId")
    fun getProjectsByColor(colorId: Long): List<Project>
    
}