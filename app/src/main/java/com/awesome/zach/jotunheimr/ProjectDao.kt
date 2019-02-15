package com.awesome.zach.jotunheimr

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.awesome.zach.jotunheimr.entities.Project

@Dao
interface ProjectDao {

    @Query("SELECT * from project_table ORDER BY name ASC")
    fun getAllProjects(): LiveData<List<Project>>

    @Insert
    fun insert(project: Project)

    @Query("DELETE FROM project_table")
    fun deleteAll()

    @Delete
    fun delete(project: Project)
}