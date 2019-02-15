package com.awesome.zach.jotunheimr

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.awesome.zach.jotunheimr.entities.Project
import com.awesome.zach.jotunheimr.entities.Task
import kotlinx.android.synthetic.main.activity_new_task.*

/**
 * Activity for entering a task.
 */

class NewTaskActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var mProjects = ArrayList<Project>()

    private lateinit var editTaskView: EditText
    private lateinit var spinner: Spinner
    private lateinit var selectedProject: Project
    private lateinit var mTaskListViewModel: TaskListViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        editTaskView = findViewById(R.id.edit_task)

        initializeSpinner()

        mTaskListViewModel = ViewModelProviders.of(this).get(TaskListViewModel::class.java)
        mTaskListViewModel.allTasks.observe(this, Observer { tasks ->

        })

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            buttonClickConfirm()
        }
    }

    private fun buttonClickConfirm() {


        val replyIntent = Intent()
        if (TextUtils.isEmpty(editTaskView.text)) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
        } else {
            saveTask()

            replyIntent.putExtra(EXTRA_REPLY, Activity.RESULT_OK)
            setResult(Activity.RESULT_OK, replyIntent)
        }
        finish()
    }

    private fun saveTask() {
        val taskName = editTaskView.text.toString()
        val project = selectedProject
        val task = Task(taskName, project.id)
        mTaskListViewModel.insert(task)
    }

    private fun initializeSpinner() {
        spinner = findViewById(R.id.spinner_projects)

        ProjectListViewModel(application).allProjects.observe(this, Observer { projects ->
            mProjects = projects?.toList() as ArrayList<Project>
        })

        val projectNames = ArrayList<String>()
        mProjects.forEach {
            projectNames.add(it.name)
        }


        val projectsArrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, projectNames)
        projectsArrayAdapter.also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_projects.adapter = adapter
        }

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when (parent.id) {
            R.id.spinner_projects -> selectedProject = mProjects[position]
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        selectedProject = mProjects[0]
    }

    companion object {
        const val EXTRA_REPLY = "REPLY"
    }
}

