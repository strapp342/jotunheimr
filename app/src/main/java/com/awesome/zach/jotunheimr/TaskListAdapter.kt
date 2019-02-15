package com.awesome.zach.jotunheimr

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.awesome.zach.jotunheimr.entities.Task
import kotlinx.android.synthetic.main.recyclerview_item_task.view.*

class TaskListAdapter internal constructor(context: Context) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tasks = emptyList<Task>() // cached copy of tasks

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTask(task: Task) {
            itemView.textView.text = task.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): TaskViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindTask(tasks[position])
    }

    override fun getItemCount() = tasks.size

    internal fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}