package com.awesome.zach.jotunheimr

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.awesome.zach.jotunheimr.entities.Project
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

class ProjectListViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()

    private val coroutineContext : CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    private val repository : JotunheimrRepository
    val allProjects : LiveData<List<Project>>

    init {
        val database = JotunheimrRoomDatabase.getDatabase(application, scope)
        val taskDao = database.taskDao()
        val projectDao = database.projectDao()
        repository = JotunheimrRepository(taskDao, projectDao)

        allProjects = repository.allProjects
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun insert(project: Project) = scope.launch(Dispatchers.IO) {
        repository.insertProject(project)
    }
}