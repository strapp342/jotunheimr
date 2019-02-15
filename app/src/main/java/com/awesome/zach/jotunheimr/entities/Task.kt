package com.awesome.zach.jotunheimr.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(
    tableName = "task_table",
    foreignKeys = [
        ForeignKey(entity = Project::class,
        parentColumns = ["id"],
        childColumns = ["projectId"],
        onDelete = ForeignKey.CASCADE)])
data class Task(
    @NonNull
    @ColumnInfo(name = "name")
    var name: String,
    @NonNull
    val projectId: Long) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
