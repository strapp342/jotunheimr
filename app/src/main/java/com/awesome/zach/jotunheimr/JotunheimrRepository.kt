package com.awesome.zach.jotunheimr

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.awesome.zach.jotunheimr.entities.Project
import com.awesome.zach.jotunheimr.entities.Task

class JotunheimrRepository(private val taskDao : TaskDao, private val projectDao: ProjectDao) {
    val allTasks : LiveData<List<Task>> = taskDao.getAllTasks()
    val allProjects : LiveData<List<Project>> = projectDao.getAllProjects()

    @WorkerThread
    fun insertTask(task : Task) : Long{
        return taskDao.insert(task)
    }

    @WorkerThread
    fun insertProject(project: Project) {
        projectDao.insert(project)
    }
}