package com.awesome.zach.jotunheimrsandbox.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.awesome.zach.jotunheimrsandbox.data.entities.Project

@Dao
interface ProjectDao {

    @Query("SELECT count(id) FROM project")
    fun count(): Int

    @Query("SELECT count(id) FROM project")
    fun has(): Boolean

    @Query("SELECT * FROM project WHERE id = :id")
    fun projectByIdNow(id: Long): Project?

    @Query("SELECT * FROM project WHERE id = :id")
    fun projectById(id: Long): LiveData<Project?>

    @Query("SELECT * FROM project ORDER BY createdAt DESC")
    fun list(): LiveData<List<Project>>

    @Query("SELECT * FROM project ORDER BY createdAt DESC")
    fun listNow(): List<Project>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg project: Project): Array<Long>

    @Query("DELETE FROM project WHERE id = :id")
    fun deleteById(id: Long): Int

    @Delete
    fun delete(project: Project): Int

    @Query("DELETE FROM project")
    fun deleteAll(): Int

    /** Old methods below */

    // // returns the id of the inserted row
    // @Insert(onConflict = OnConflictStrategy.REPLACE)
    // fun insertProject(project: Project): Long
    //
    // // returns a list of inserted row ids
    // @Insert(onConflict = OnConflictStrategy.REPLACE)
    // fun bulkInsertProjects(projects: List<Project>): List<Long>
    //
    // // returns a count of updated rows
    // @Update
    // fun updateProject(project: Project): Int
    //
    // // returns a count of updated rows
    // @Update
    // fun bulkUpdateProjects(projects: List<Project>): Int
    //
    // // returns a count of deleted rows
    // @Delete
    // fun deleteProject(project: Project): Int
    //
    // // returns a count of deleted rows
    // @Delete
    // fun bulkDeleteProjects(projects: List<Project>): Int

    // // returns a count of deleted rows
    // @Query("DELETE FROM project_table")
    // fun deleteAllProjects(): Int
    //
    // @Query("SELECT project_table.id, project_table.projectName, project_table.id, color_table.hex FROM project_table INNER JOIN color_table ON project_table.id = color_table.id ORDER BY project_table.id ASC")
    // fun getAllProjects(): List<Project>
    //
    // @Query("SELECT project_table.id, project_table.projectName, project_table.id, color_table.hex FROM project_table INNER JOIN color_table ON project_table.id = color_table.id ORDER BY project_table.id ASC")
    // fun getAllProjectsLive(): LiveData<List<Project>>
    //
    // @Query("SELECT * FROM project_table WHERE id == :id")
    // fun getProjectById(projectId: Long): Project
    //
    // @Query("SELECT * FROM project_table WHERE projectName == :name ORDER BY id ASC")
    // fun getProjectsByName(name: String): List<Project>
    //
    // @Query("SELECT * FROM project_table WHERE id == :id ORDER BY id ASC")
    // fun getProjectsByColor(colorId: Long): List<Project>
}