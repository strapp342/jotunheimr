package com.awesome.zach.jotunheimr

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.awesome.zach.jotunheimr.entities.Project
import com.awesome.zach.jotunheimr.entities.Task
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.launch

@Database(entities = [Project::class, Task::class], version = 1)
public abstract class JotunheimrRoomDatabase : RoomDatabase() {
    abstract fun taskDao() : TaskDao
    abstract fun projectDao() : ProjectDao

    companion object {
        @Volatile
        private var INSTANCE: JotunheimrRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): JotunheimrRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                // create the database here
                val instance = Room.databaseBuilder(context.applicationContext, JotunheimrRoomDatabase::class.java,
                    "jotunheimr_database").addCallback(JotunheimrDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class JotunheimrDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.taskDao(), database.projectDao())
                }
            }
        }

        fun populateDatabase(taskDao: TaskDao, projectDao: ProjectDao) {

            // insert a few projects for dev
            // delete all first
            projectDao.deleteAll()

            val projectInbox = Project("Inbox")
            projectDao.insert(projectInbox)
            val projectJotunheimr = Project("Jotunheimr")
            projectDao.insert(projectJotunheimr)

            // insert a few tasks for dev
            // delete all first
            taskDao.deleteAll()

            var task = Task("watch the film", 1)
            taskDao.insert(task)
            task = Task("borrow 2 cameras from MGM", 1)
            taskDao.insert(task)
            task = Task("show me all the blueprints", 1)
            taskDao.insert(task)



        }
    }
}