package com.awesome.zach.jotunheimr

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.awesome.zach.jotunheimr.entities.Task

@Dao
interface TaskDao {

    @Query("SELECT * from task_table ORDER BY name ASC")
    fun getAllTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task) : Long

    @Query("DELETE FROM task_table")
    fun deleteAll()

    @Delete
    fun delete(task: Task)
}